package com.xera.sanadqrreader.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xera.sanadqrreader.R

@Composable
fun CustomBackButton(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
            .background(Color(0xFFF7F8F9), shape = RoundedCornerShape(8.dp))
            .size(42.dp)
            .clickable { onBackPressed() }
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.back_arrow),
            contentDescription = "Back Button",
            modifier = Modifier.size(32.dp)
        )
    }
}


@Preview
@Composable
fun PreviewCustomBackButton() {
CustomBackButton {

}
}