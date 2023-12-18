package com.xera.sanadqrreader.ui.scan_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ScanScreen(
    viewModel: ScanScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    ScanScreenContent(
        state = state,
        onStartScanningClicked = viewModel::startScanning
    )

}
@Composable
private fun ScanScreenContent(
    state: ScanScreenUiState,
    onStartScanningClicked: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        LazyColumn (
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            items(state.details.size) { index ->
                QrCodeItem(state = state.details[index])
            }
        }
        Button(
            onClick = { onStartScanningClicked()},
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)

        ) {
            Text(text = "Scan")
        }
    }
}


@Preview
@Composable
fun PreviewScanScreenContent() {
    ScanScreenContent(state = ScanScreenUiState()) {
        
    }
    
}