package com.xera.sanadqrreader.ui.scan_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xera.sanadqrreader.ui.receive_screen.navigateToReceiveScreen
import com.xera.sanadqrreader.ui.send_screen.navigateToSendScreen

@Composable
fun ScanScreen(
    navController: NavController
) {
    ScanScreenContent(
        onInButtonClicked  = navController::navigateToReceiveScreen,
        onOutButtonClicked = navController::navigateToSendScreen
    )
}

@Composable
private fun ScanScreenContent (
    onInButtonClicked : () -> Unit,
    onOutButtonClicked : () -> Unit
) {

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        Row (
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Button(onClick = { onInButtonClicked() },
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically)) {
                Text(text = "In")
            }
            Button(onClick = { onOutButtonClicked() },
                modifier = Modifier.weight(1f)) {
                Text(text = "Out")
            }
        }

    }

}


@Preview
@Composable
fun PreviewScanScreenContent() {
    ScanScreenContent(onInButtonClicked = { /*TODO*/ }) {

    }
}