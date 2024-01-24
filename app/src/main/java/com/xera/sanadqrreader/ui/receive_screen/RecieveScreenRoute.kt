package com.xera.sanadqrreader.ui.receive_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.xera.sanadqrreader.navigation.SanadQrReaderDestination

private const val ROUTE = SanadQrReaderDestination.RECEIVE_SCREEN

fun NavController.navigateToReceiveScreen() {
    navigate(ROUTE)
}

fun NavController.navigateBackToReceiveScreen() {
    popBackStack(ROUTE, inclusive = true)
}

fun NavGraphBuilder.receiveScreen(navController: NavHostController) {
    composable(ROUTE) { ReceiveScreen(navController = navController) }
}