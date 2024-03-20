package com.xera.sanadqrreader.ui.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xera.sanadqrreader.R

@Composable
fun CustomEditText(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    hint: String = "Enter your name",
    isPassword: Boolean = true,
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    TextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChanged(it) },
        readOnly = false,

        placeholder = {
            Text(
                hint, style = TextStyle(
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedContainerColor = Color(0xFFF7F8F9),
            unfocusedContainerColor = Color(0xFFF7F8F9)
        ),
        shape = RoundedCornerShape(16.dp),
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        painter = painterResource(id = if (passwordVisibility) R.drawable.password_eye else R.drawable.closed_eye),
                        contentDescription = "Toggle password visibility"
                    )
                }
            }
        },
        visualTransformation = if (isPassword && !passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None
    )
}


@Preview
@Composable
fun PreviewCustomEditText() {
    CustomEditText(value = "", onValueChanged = {})
}