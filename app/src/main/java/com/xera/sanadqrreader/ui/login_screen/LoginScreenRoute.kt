package com.xera.sanadqrreader.ui.login_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.xera.sanadqrreader.navigation.SanadQrReaderDestination

private const val ROUTE = SanadQrReaderDestination.LOGIN_SCREEN

fun NavController.navigateToLoginScreen() {
    navigate(ROUTE)
}

fun NavController.backToLoginScreen() {
    popBackStack(ROUTE, false)
}

fun NavGraphBuilder.loginScreen(navController: NavController) {
    composable(route = ROUTE) { LoginScreen(navController = navController) }
}