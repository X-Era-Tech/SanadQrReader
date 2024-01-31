package com.xera.sanadqrreader.ui.product_history

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.xera.sanadqrreader.navigation.SanadQrReaderDestination

private const val ROUTE = SanadQrReaderDestination.PRODUCT_HISTORY

fun NavController.navigateToProductHistoryScreen(qrCode:String) {
    navigate("$ROUTE/$qrCode")
}

fun NavGraphBuilder.productHistoryRoute(navController: NavController) {
    composable(
        route ="$ROUTE/{${ProductHistoryArgs.QR_CODE}}",
        arguments = listOf(
            navArgument(ProductHistoryArgs.QR_CODE) { NavType.StringType },
        )
    ) { ProductHistoryScreen(navController = navController) }
}






class ProductHistoryArgs(savedStateHandle: SavedStateHandle){
    val qrCode: String = savedStateHandle[QR_CODE] ?: "0"

    companion object{
        const val QR_CODE = "qrCode"
    }
}