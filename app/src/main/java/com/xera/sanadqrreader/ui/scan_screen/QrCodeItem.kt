package com.xera.sanadqrreader.ui.scan_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QrCodeItem(
    state: QrCodes
) {
    var expanded by remember { mutableStateOf(false) }

    val statusBoxColor = if (state.states == "in stock") Color(0xFF4CAF50) else Color.Red

    Box(
        modifier = Modifier
            .clickable { expanded = !expanded }
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0XFFB6BBC4))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "id = ${state.qrCode.toString()}",
                    maxLines = 2,
                    modifier = Modifier.padding(4.dp),
                )
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = statusBoxColor)
                ) {
                    Text(
                        text = state.states.toString(),
                        style = TextStyle(color = Color.White),
                        modifier = Modifier.padding(4.dp),
                    )
                }
            }

            if (expanded) {
                Text(text = "Get In Time: ${state.getInTime}")
                Text(text = "Get Out Time: ${state.getOutTime}")
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
            states = "In stock",
            "",
            ""
        )
    )
}