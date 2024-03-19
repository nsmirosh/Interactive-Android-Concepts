package nick.mirosh.androidsamples.ui.jetpack_compose.textfield

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat

private const val TAG = "TextFieldFormatting"
//[ ] make sure placeholder is completely erased when user starts typing
//[ ] when jumping to another field the placholder should reappear

@Composable
fun TextFieldFormatting() {
    Column {
        val placeholderValue = "0.00"


        var amount by remember { mutableStateOf("") }

        var formattedAmount2 by remember { mutableStateOf(TextFieldValue("")) }
        var isFocused by remember { mutableStateOf(false) }

        Box {
            BasicTextField(
                value = formattedAmount2,
                onValueChange = { newValue ->
                    Log.d(TAG, "TextFieldFormatting: newValue before regex: ${newValue.text}")
                    Log.d(
                        TAG,
                        "TextFieldFormatting: newValue before regex after formatting: ${
                            newValue.text.replace(
                                ",",
                                ""
                            )
                        }"
                    )
                    if (newValue.text
                        .replace(",", "")
                        .matches("^\\d*\\.?\\d{0,2}$".toRegex())) {
                        Log.d(TAG, "TextFieldFormatting: newValue: $newValue")
                        amount = newValue.text.replace(",", "")
                        formattedAmount2 = formatTextFieldvalue(newValue)
                    }
                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                textStyle = TextStyle(
                    color = if (isFocused) Color.Blue else
                        Color.Black,
                    fontSize = 14.sp,
                ),
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .onFocusChanged {
                        isFocused = it.isFocused
                    }
                    .border(
                        1.dp,
                        if (isFocused) Color.Blue else Color.Black,
                        RoundedCornerShape(10.dp)
                    )
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 8.dp, top = 10.dp, bottom = 10.dp),
                decorationBox = { innerTextField ->
                    Box {
                        if (formattedAmount2.text.isEmpty()) {
                            Text("0.00", color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            )

        }

    }


}

fun formatNumber(input: String): String =
    try {
        val formatter = DecimalFormat("#,###,###")
        Log.d(
            "LimitBoost", "Formatted: ${
                formatter.format(
                    input
                        .replace(",", "")
                        .toDouble()
                )
            }"
        )
        formatter.format(
            input.replace(",", "")
                .toDouble()
        )
    } catch (e: NumberFormatException) {

        Log.d("LimitBoost", "Error: ${e.message}")
        input
    }

fun formatTextFieldvalue(input: TextFieldValue): TextFieldValue =
    try {
        Log.d(TAG, "input selection : ${input.selection} ")
        Log.d(TAG, "input composition : ${input.composition} ")
        //TODO decimal format only for API over 24
        val formatted = formatString(input.text.replace(",", ""))
        input.copy(
            text = formatted,
            selection = TextRange(formatted.length, formatted.length)
        )


//        input.copy(
//            text = input.text,
//            selection = TextRange(input.text.length, input.text.length)
//        )

    } catch (e: NumberFormatException) {

        Log.d(TAG, "Error: ${e.message}")
        input
    }


fun formatString(input: String): String {
    if (input.startsWith('.')) return input

    val parts = input.split(".")
    val beforeDecimal = parts[0]
    val afterDecimal = if (parts.size > 1) "." + parts[1] else ""

    val formattedBeforeDecimal = beforeDecimal.reversed().chunked(3).joinToString(",").reversed()

    return formattedBeforeDecimal + afterDecimal
}
