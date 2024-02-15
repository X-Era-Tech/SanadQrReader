package com.xera.sanadqrreader.ui.send_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xera.sanadqrreader.ui.product_history.navigateToProductHistoryScreen
import com.xera.sanadqrreader.ui.receive_screen.DropDownMenu

@Composable
fun SendScreen(
    navController: NavController,
    viewModel: SendScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    SendScreenContent(
        state = state,
        onSendClicked = viewModel::starScanOutProducts,
        onSelectedToChange = viewModel::onToChanged,
        onMoreClicked = navController::navigateToProductHistoryScreen
    )
}

@Composable
private fun SendScreenContent(
    state: SendScreenUiState,
    onSendClicked: () -> Unit,
    onSelectedToChange: (String) -> Unit,
    onMoreClicked : (String) -> Unit
) {
    val toWarehouse = listOf("Giza", "Cairo", "india")
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
            DropDownMenu(
                dropDownMenuList = toWarehouse,
                dropDownMenuLabel = "To",
                selectedText = state.toWarehouse,
                onSelectedTextChange = onSelectedToChange,
                modifier = Modifier
                    .weight(1f)
            )

            OutlinedTextField(
                value = state.fromWarehouse,
                textStyle = TextStyle(color = Color.Black),
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                label = { Text("From") },
                readOnly = true
            )
        }
        LazyColumn(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(state.details) { index ,item ->
                OutProductItem(state = state.details[index]){
                    item.qrCode?.let { qrCode -> onMoreClicked(qrCode) }
                }
            }
            item {
                if (state.toWarehouse != "" && state.fromWarehouse != "") {
                    enable = true
                }
                Button(
                    onClick = { onSendClicked() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    enabled = enable

                ) {
                    Text(text = "Out")
                }

            }
        }

    }
}