package com.xera.sanadqrreader.ui.product_history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.unit.dp

@Composable
fun ProductHistoryItem(
    state: History,
    )
 {
    var expanded by remember { mutableStateOf(false) }
     val statusBoxColor = if (state.status == "in") Color(0xFF4CAF50) else Color.Red

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
                    text = state.status.toString(),
                    style = TextStyle(color = Color.White),
                    modifier = Modifier.padding(4.dp),
                )
            }


            if (expanded) {
                if (state.getInTime?.isNotEmpty()!!) {
                    Text(text = "Last Get In: ${state.getInTime}")
                }
                if (state.getOutTime?.isNotEmpty() == true) {
                    Text(text = "Last Get Out: ${state.getOutTime}")
                }
                Text(text = "From: ${state.from}")
                Text(text = "To: ${state.to}")
            }
        }
    }
}
