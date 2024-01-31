package com.xera.sanadqrreader.ui.product_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ProductHistoryScreen(
    navController: NavController,
    viewModel: ProductHistoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    ProductHistoryContentScreen(state = state)
}


@Composable
private fun ProductHistoryContentScreen (
    state: ProductHistoryUiState
) {

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Text(text = "Product History", style = TextStyle(fontSize = 24.sp
        ),modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .align(Alignment.CenterHorizontally).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            items(state.details.size){
                ProductHistoryItem(state.details[it])
            }
        }
    }

}

@Preview
@Composable
fun PreviewProductHistoryScreen() {
    ProductHistoryContentScreen(state = ProductHistoryUiState())
}