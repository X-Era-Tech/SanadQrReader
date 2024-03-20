package com.xera.sanadqrreader.ui.receive_screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xera.sanadqrreader.ui.product_history.navigateToProductHistoryScreen

@Composable
fun ReceiveScreen(
    navController: NavController,
    viewModel: ScanScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    ReceiveScreenContent(
        state = state,
        onStartScanningClicked = viewModel::startScanning,
        onSelectedFromChange = viewModel::onFromChanged,
        onMoreClicked = navController::navigateToProductHistoryScreen,
        saveExcel = viewModel::writeProductsToExcel
    )

}

@Composable
private fun ReceiveScreenContent(
    state: ScanScreenUiState,
    onStartScanningClicked: () -> Unit,
    onSelectedFromChange: (String) -> Unit,
    onMoreClicked: (String) -> Unit,
    saveExcel: (Context) -> Unit
) {
    val fromWarehouses = listOf("Maadi", "Giza", "Cairo")
    var enable by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = state.toWarehouse,
                textStyle = TextStyle(color = Color.Black),
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                label = { Text("To") },
                readOnly = true
            )
            DropDownMenu(
                dropDownMenuList = fromWarehouses,
                dropDownMenuLabel = "From",
                selectedText = state.fromWarehouse,
                onSelectedTextChange = onSelectedFromChange,
                modifier = Modifier
                    .weight(1f)
            )
            Log.i("Darkness", state.fromWarehouse)
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                color = Color(0xFF35C2C1),
                strokeWidth = 2.dp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(state.details) { index, item ->
                    QrCodeItem(state = state.details[index]) {
                        item.qrCode?.let { qrCode -> onMoreClicked(qrCode) }
                    }
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.End)) {
            FloatingActionButton(
                onClick = {},
                content = {
                    Icon(Icons.Filled.Share, contentDescription = "Save Workbook")
                    saveExcel(LocalContext.current)
                }
            )
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        if (state.toWarehouse != "" && state.fromWarehouse != "") {
            enable = true
        }

        Button(
            onClick = { onStartScanningClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            enabled = enable
        ) {
            Text(text = "In")
        }
    }
}


@Preview
@Composable
fun PreviewScanScreenContent() {
    ReceiveScreenContent(state = ScanScreenUiState(), {}, {},{}) {

    }

}