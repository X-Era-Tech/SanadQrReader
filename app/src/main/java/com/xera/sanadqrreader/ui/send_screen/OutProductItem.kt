package com.xera.sanadqrreader.ui.send_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp

@Composable
fun OutProductItem(
    state: OutProductsQrCode,
    onMoreClicked: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val statusBoxColor = if (state.states == "in") Color(0xFF4CAF50) else Color.Red

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
                Text(text = "To: ${state.to}")
                Text(text = "Get Out Time: ${state.getOutTime}")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = { state.qrCode?.let { onMoreClicked(it) } }) {
                        Text("more")
                    }
                }
            }
        }
    }
}
