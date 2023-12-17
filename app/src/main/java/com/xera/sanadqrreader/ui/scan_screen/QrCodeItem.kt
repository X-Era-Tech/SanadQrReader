package com.xera.sanadqrreader.ui.scan_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QrCodeItem(
    state : QrCodes
){
    val statusBoxColor = if (state.states == "in stock")  Color(0xFF4CAF50) else Color.Red
    Box(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0XFFB6BBC4))
            .padding(16.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "id = ${state.qrCode.toString()}",
                modifier = Modifier.padding(4.dp),
                )
            Box(modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(8.dp))
                .background(color = statusBoxColor)
            ){
                Text(
                    text = state.states.toString(),
                    style = TextStyle(color = Color.White),
                    modifier = Modifier.padding(4.dp),

                )
            }
        }
    }
}

@Preview
@Composable
fun QrCodeItemPreview(){
    QrCodeItem(
        state = QrCodes(
            qrCode = "123456789",
            states = "In stock"
        )
    )
}