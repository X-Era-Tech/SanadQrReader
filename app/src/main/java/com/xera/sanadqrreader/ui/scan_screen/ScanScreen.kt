package com.xera.sanadqrreader.ui.scan_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xera.sanadqrreader.ui.receive_screen.navigateToReceiveScreen
import com.xera.sanadqrreader.ui.send_screen.navigateToSendScreen

@Composable
fun ScanScreen(
    navController: NavController,
    viewModel: IntroScreenViewModel = hiltViewModel()
) {
    ScanScreenContent(
        onInButtonClicked = navController::navigateToReceiveScreen,
        onOutButtonClicked = navController::navigateToSendScreen,
        onLogOutButton = viewModel::logOut,
        navController = navController

    )
}

@Composable
private fun ScanScreenContent(
    navController: NavController,
    onInButtonClicked: () -> Unit,
    onOutButtonClicked: () -> Unit,
    onLogOutButton: (NavController) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onInButtonClicked() },
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = "In")
                }
                Button(
                    onClick = { onOutButtonClicked() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Out")
                }
        }
        Button(
            onClick = { onLogOutButton(navController) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Logout")
        }

    }

}

