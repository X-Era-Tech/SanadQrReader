package com.xera.sanadqrreader.ui.send_screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
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
        onMoreClicked = navController::navigateToProductHistoryScreen,
        saveExcel = viewModel::writeProductsToExcel
    )
}

@Composable
private fun SendScreenContent(
    state: SendScreenUiState,
    onSendClicked: () -> Unit,
    onSelectedToChange: (String) -> Unit,
    onMoreClicked: (String) -> Unit,
    saveExcel: (Context) -> Unit
) {
    var toWarehouse by remember { mutableStateOf( listOf(
        "Abdallah Hassan Ahmed Mohamed",
        "Abdelrahman Kamal Ahmed",
        "Ahmed Ali Nagy Monzee",
        "Ahmed Ali Saleh Ahmed",
        "Ahmed Assem Abbas Sobhy",
        "Ahmed Gamal Hadad",
        "Ahmed Kamal Ramdan Ahmed",
        "Ahmed Lamiee Hassan Hamouda",
        "Ahmed Moustafa Hassan",
        "Ahmed Said Mohamed Ali",
        "Ahmed Said Sayed Mohamed",
        "Ahmed Sobhy Mahdy Ahmed",
        "Ali Ahmed Mohamed Mariee",
        "Ali Hassan Ahmed Mohamed",
        "Ali Mohamed Abdelazim Ahmed",
        "Ali Mohamed Saneed Mohamed",
        "Amir Mostafa Muhammad Mostafa",
        "Amr Magdy ghoneam Hussien",
        "Ehab romany Amen Ibrahim",
        "Emad Hamdy Ahmed Soliman",
        "Eslam Motamad Abdelhameed Mohamed",
        "Hossam El Din Khaled Abdelhalem Abdrabo",
        "Hussien Amin Hussien Amin",
        "Islam Lamiee Hassan Hamouda",
        "Islam Saber Ibrahim Ahmed",
        "Karim Ahmed Ali Ismaeil",
        "Marwan Hussien Ahmed Mohamed",
        "Mohamed Abo Talep Mohamed Abo Talep",
        "Mohamed Adel Said El Sayed",
        "Mohamed Elsayed Abdelfatah Badawy",
        " Mohamed Gama lAbdelrazik Radwan",
        "Mohamed Hassan Mohamed",
        "Mohamed Lamiee Hassan Hamouda",
        "Mohamed Mohamed Mohamed ElHosarry",
        "Mohamed Moustafa Abdallah Helal",
        "Moustafa Abdallah Helal El Hossary",
        "Moustafa Khalifa Hassan Ahmed",
        "Rami Ahmed Attia Hassan",
        "Said Mohamedy Abdelaziz Hasanien",
        "Soliman El Sayed Soliman Ahmed",
        "Zeyad Mahmoud Nagy Monzee"

    ))}
    var enable by remember { mutableStateOf(false) }
    var isTextFieldVisible by remember { mutableStateOf(false) }
    var newCustomer by remember { mutableStateOf("") }

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
//            Log.i("SendScreenContent", "isAdmin: ${state.isAdmin}")
//            if (state.isAdmin) {
//                IconButton(onClick = { isTextFieldVisible = !isTextFieldVisible },modifier = Modifier.padding(8.dp)) {
//                    Icon(Icons.Filled.Add, contentDescription = "Add")
//                }
//            }
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
        Spacer(modifier = Modifier.padding(8.dp))
        if (isTextFieldVisible) {
            OutlinedTextField(
                value = newCustomer,
                onValueChange = { newCustomer = it },
                label = { Text("New Customer") }
            )

            Button(onClick = {
                toWarehouse = toWarehouse + newCustomer
                newCustomer = ""
                isTextFieldVisible = false
            }) {
                Text("Save Customer")
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        if (state.isLoading) {
            CircularProgressIndicator(
                color = Color(0xFF35C2C1),
                strokeWidth = 2.dp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(state.details) { index, item ->
                OutProductItem(state = state.details[index]) {
                    item.qrCode?.let { qrCode -> onMoreClicked(qrCode) }
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
