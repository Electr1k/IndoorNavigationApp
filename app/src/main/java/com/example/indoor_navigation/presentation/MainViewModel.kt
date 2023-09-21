package com.example.indoor_navigation.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.indoor_navigation.R
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.enableRotation
import ovh.plrapps.mapcompose.api.moveMarker
import ovh.plrapps.mapcompose.api.onLongPress
import ovh.plrapps.mapcompose.api.onMarkerClick
import ovh.plrapps.mapcompose.api.onMarkerMove
import ovh.plrapps.mapcompose.api.onTap
import ovh.plrapps.mapcompose.api.setScrollOffsetRatio
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.state.MapState

class MainViewModel: ViewModel() {

    private lateinit var _context: Context
    val currentLocation = mutableStateOf("СКБ «КИТ»")
    val isHigh = mutableStateOf(true)  // спрятан ли bottom sheet

    /**
     * @Parem [ID_POSITION] - идентификатор маркера позиции
     * @Parem [ID_FINISH] - идентификатор маркера конца маршрута
     * */
    private val ID_POSITION = "id_position"
    private val ID_FINISH = "id_finish"

    private val tileStreamProvider = TileStreamProvider { row, col, zoomLvl ->
        try {
            _context.assets?.open("tiles/$zoomLvl/$row/$col.jpg")
        } catch (e: Exception) {
            _context.assets?.open("tiles/blank.png")
        }
    }

    val state =  mutableStateOf(
        MapState(5, 3840, 2160, 256){
            scale(0f)
        }.apply {
            addLayer(tileStreamProvider)
            enableRotation()
            onMarkerMove { id, x, y, _, _ ->
                println("move $id $x $y")
            }
            onMarkerClick { id, x, y ->
                println("marker click $id $x $y")
            }
            onTap { x, y ->
                println("on tap $x $y")
            }
            onLongPress { x, y ->
                println("on long press $x $y")
            }

            setScrollOffsetRatio(0.5f, 0.5f)
        }
    )

    /**
     * Метод для размещения маркера текущего положения пользователя
     * @Param [x] - X координата метки
     * @Param [y] - Y координата метки
     * */
    internal fun setPositionMarker(x: Double = 0.5, y: Double = 0.5){
        try {
            state.value.addMarker(ID_POSITION, x, y) {
                Icon(
                    painter = painterResource(id = R.drawable.position_marker),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp),
                    tint = Color(0xffff0000)
                )
            }
        }catch (e: Exception){
            state.value.moveMarker("id_position", x, y)
        }
    }

    /**
     * Метод для размещения маркера конечной точки маршрута
     * @Param [x] - X координата метки
     * @Param [y] - Y координата метки
     * */
    internal fun setFinishMarker(x: Double = 0.6, y: Double = 0.6){
        try {
            state.value.addMarker(ID_FINISH, x, y) {
                Icon(
                    painter = painterResource(id = R.drawable.finish_marker),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    tint = Color(0xffff0000)
                )
            }
        }catch (e: Exception){
            state.value.moveMarker("id_finish", x, y)
        }
    }

    /**
     * Метод для размещения маркеров(названий) аудиторий
     * @Param [id] - идентификатор маркера
     * @Param [name] - значение текста маркера
     * @Param [x] - X координата метки
     * @Param [y] - Y координата метки
     * */
    internal fun addDefaultMarker(id: String, name: String, x: Double = 0.6, y: Double = 0.6){
        try {
            state.value.addMarker(id, x, y) {
                Icon(
                    bitmap = drawText(name).asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    tint = Color(0xffffffff)
                )
            }
        }catch (e: Exception){
            Log.e("ErrorsMainVM", e.message.toString())
        }
    }

    /**
     * Функция отрисовки текста на карте
     * @Param [text] - отображаемый текст
     * @Param [textColor] - цвет отображаемого текста
     * @Param [textSize] - размер шрифта текста
     * @Param [typeface] - семейство шрифтов
     * @Param [style] - стиль шрифта
     * @Param [isUnderline] - наличие подчеркивания
     * @Return возвращает карту с отрисованным на ней текстом
     * */
    private fun drawText(
        text: String = "Г-320",
        textColor: Int = android.graphics.Color.WHITE,
        textSize: Float = 18F * 2,
        typeface: Typeface = Typeface.SERIF,
        style: Int = Typeface.BOLD,
        isUnderline: Boolean = false,
    ): Bitmap {
        val bitmap = Bitmap.createBitmap(
            (textSize.toInt() * text.length / 1.6).toInt(),
            textSize.toInt(),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap).apply {}

        val paint = Paint().apply {
            isAntiAlias = true
            color = textColor
            this.textSize = textSize
            this.typeface = typeface
            setTypeface(Typeface.create(typeface, style))
            if (isUnderline) {
                flags = Paint.UNDERLINE_TEXT_FLAG
            }
        }
        canvas.drawText(text, 10F, canvas.height.toFloat(), paint)
        return bitmap
    }

    fun setHigh(value: Boolean){
        isHigh.value = value
    }

    fun setLocation(value: String){
        currentLocation.value = value
    }

    fun setContext(context: Context){
        _context = context
    }
}