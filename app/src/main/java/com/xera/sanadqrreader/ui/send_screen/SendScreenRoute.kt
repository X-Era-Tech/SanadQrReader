package com.xera.sanadqrreader.ui.send_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.xera.sanadqrreader.navigation.SanadQrReaderDestination

private const val ROUTE = SanadQrReaderDestination.SEND_SCREEN

fun NavController.navigateToSendScreen() {
    navigate(ROUTE)
}

fun NavController.navigateBackToSendScreen() {
    popBackStack(ROUTE, inclusive = true)
}

fun NavGraphBuilder.sendScreen(navController: NavHostController) {
    composable(ROUTE) { SendScreen(navController) }
}