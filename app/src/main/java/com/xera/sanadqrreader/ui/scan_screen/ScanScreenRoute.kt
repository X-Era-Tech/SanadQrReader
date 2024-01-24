package com.xera.sanadqrreader.ui.scan_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.xera.sanadqrreader.navigation.SanadQrReaderDestination

private const val ROUTE = SanadQrReaderDestination.SCAN_SCREEN

fun NavController.navigateToScanScreen() {
    navigate(ROUTE)
}

fun NavController.navigateBackToScanScreen() {
    popBackStack(ROUTE, inclusive = true)
}

fun NavGraphBuilder.scanScreen(navHostController: NavHostController){
    composable(ROUTE){
        ScanScreen(navController = navHostController)
    }
}