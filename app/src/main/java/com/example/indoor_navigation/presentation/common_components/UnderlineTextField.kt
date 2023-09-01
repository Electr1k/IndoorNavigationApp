
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.indoor_navigation.ui.theme.DarkBlue
import com.example.indoor_navigation.ui.theme.DarkGrey
import com.example.indoor_navigation.ui.theme.LightBlue
import com.example.indoor_navigation.ui.theme.LightGray

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UnderlineTextField(
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    value: String,
    placeholder: String = "",
    localFocusManager: FocusManager,
    isUncorrected: Boolean = false,
    keyboardController: SoftwareKeyboardController? = null,
    maxLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onDoneKey: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(
        onNext = {
            localFocusManager.moveFocus(FocusDirection.Down)
        },
        onDone = {
            localFocusManager.clearFocus()
            keyboardController?.hide()
            onDoneKey?.invoke()
        }
    ),
    textMessage: String? = null,
    onValueChange: (String) -> Unit
) {
    fun placeholderVisualTransformation(): TransformedText {
        val annotatedString = AnnotatedString.Builder().run {
            pushStyle(SpanStyle(color = DarkGrey, fontSize = 18.sp))
            append(placeholder)
            toAnnotatedString()
        }
        return TransformedText(annotatedString, OffsetMapping.Identity)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val customTextSelectionColors = TextSelectionColors(
            handleColor = DarkBlue,
            backgroundColor = LightBlue
        )

        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            BasicTextField(
                readOnly = readOnly,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-2).dp)
                    .focusable(enabled = !readOnly)
                    .clickable(enabled = !readOnly) {},
                value = value,
                textStyle = if (isUncorrected) TextStyle(color = Color.Red, fontSize = 18.sp) else MaterialTheme.typography.body1,
                onValueChange = onValueChange,
                maxLines = maxLines,
                singleLine = maxLines == 1,
                visualTransformation = {
                    if (value.isEmpty()) placeholderVisualTransformation()
                    else visualTransformation.filter(it)
                },
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions,
            )
        }
        if (textMessage != null && isUncorrected){
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = textMessage,
                style = TextStyle(color = Color.Red, fontSize = 12.sp),
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UnderlineTextFieldWithBorder(
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    value: String,
    placeholder: String = "",
    localFocusManager: FocusManager,
    isUncorrected: Boolean = false,
    keyboardController: SoftwareKeyboardController? = null,
    maxLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onDoneKey: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(
        onNext = {
            localFocusManager.moveFocus(FocusDirection.Down)
        },
        onDone = {
            localFocusManager.clearFocus()
            keyboardController?.hide()
            onDoneKey?.invoke()
        }
    ),
    onValueChange: (String) -> Unit
){
    fun placeholderVisualTransformation(): TransformedText {
        val annotatedString = AnnotatedString.Builder().run {
            pushStyle(SpanStyle(color = LightGray, fontSize = 17.sp))
            append(placeholder)
            toAnnotatedString()
        }
        return TransformedText(annotatedString, OffsetMapping.Identity)
    }
    Column(modifier = modifier) {

        Box(
            modifier = Modifier
                .wrapContentSize()
                .border(1.dp, color = LightGray, RoundedCornerShape(4.dp))
                .padding(vertical = 7.dp, horizontal = 15.dp),
            contentAlignment = Alignment.Center
        ) {
            val customTextSelectionColors = TextSelectionColors(
                handleColor = DarkBlue,
                backgroundColor = LightBlue
            )

            CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                BasicTextField(
                    readOnly = readOnly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .offset(y = (-2).dp)
                        .focusable(enabled = !readOnly)
                        .clickable(enabled = !readOnly) {},
                    value = value,
                    textStyle = if (isUncorrected) TextStyle(
                        color = Color.Red,
                        fontSize = 18.sp
                    ) else MaterialTheme.typography.body1,
                    onValueChange = onValueChange,
                    maxLines = maxLines,
                    singleLine = maxLines == 1,
                    visualTransformation = {
                        if (value.isEmpty()) placeholderVisualTransformation()
                        else visualTransformation.filter(it)
                    },
                    keyboardActions = keyboardActions,
                    keyboardOptions = keyboardOptions,
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFFfFfFfF,
)
@Composable
fun PreviewUnderlineTextField() {
    UnderlineTextFieldWithBorder(
        modifier = Modifier.wrapContentHeight(),
        value = "",
        onValueChange = {  },
        maxLines = 1,
        placeholder = "Откуда",
        localFocusManager = LocalFocusManager.current,
        onDoneKey = { }
    )
}