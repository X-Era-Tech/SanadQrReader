package com.xera.sanadqrreader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.xera.sanadqrreader.ui.product_history.productHistoryRoute
import com.xera.sanadqrreader.ui.receive_screen.receiveScreen
import com.xera.sanadqrreader.ui.scan_screen.scanScreen
import com.xera.sanadqrreader.ui.send_screen.sendScreen


@Composable
fun SanadQrReaderGraph(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = SanadQrReaderDestination.SCAN_SCREEN){
        scanScreen(navHostController)
        sendScreen(navHostController)
        receiveScreen(navHostController)
        productHistoryRoute(navHostController)
    }
}


object SanadQrReaderDestination{
    const val SCAN_SCREEN = "scan_screen"
    const val SEND_SCREEN = "send_screen"
    const val RECEIVE_SCREEN = "receive_screen"
    const val PRODUCT_HISTORY = "product_hisotry"
}