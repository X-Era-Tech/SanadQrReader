package com.xera.sanadqrreader.ui.register_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.xera.sanadqrreader.navigation.SanadQrReaderDestination

private const val ROUTE = SanadQrReaderDestination.REGISTER_SCREEN


fun NavController.navigateToRegisterScreen() {
    navigate(ROUTE)
}


fun NavGraphBuilder.registerScreen(navController: NavController) {
    composable(route = ROUTE) { RegisterScreen(navController = navController) }
}