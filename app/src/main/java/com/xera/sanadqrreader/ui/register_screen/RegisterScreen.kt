package com.xera.sanadqrreader.ui.register_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xera.sanadqrreader.navigation.SanadQrReaderDestination
import com.xera.sanadqrreader.ui.composables.CustomBackButton
import com.xera.sanadqrreader.ui.composables.CustomEditText
import com.xera.sanadqrreader.ui.login_screen.navigateToLoginScreen

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    RegisterScreenContent(
        navController = navController,
        state = state,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onNameChange = viewModel::onNameChange,
        onRegisterClicked = viewModel::register,
        onBackClicked = navController::navigateToLoginScreen
    )

}

@Composable
private fun RegisterScreenContent(
    navController: NavController,
    state: RegisterUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onRegisterClicked: (String, String, String) -> Unit,
    onBackClicked: () -> Unit
) {

    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val navigatedToScanScreen = rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(key1 = state.isSuccess, key2 = state.error) {
        when {
            state.isSuccess -> {
                navigatedToScanScreen.value = true
                navController.navigate(SanadQrReaderDestination.SCAN_SCREEN) {
                    popUpTo(SanadQrReaderDestination.LOGIN_SCREEN) { inclusive = true }
                }
                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show()
            }

            state.error.isNotEmpty() -> {
                showDialog.value = true
                Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show()
            }
        }
    }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {
                Text(
                    text = "Error",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.15.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                )
            },
            text = {
                Text(
                    text = state.error,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.15.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = { showDialog.value = false },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color(0xFF35C2C1)
                    )
                ) {
                    Text(
                        text = "OK",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.15.sp,
                            fontFamily = FontFamily.SansSerif
                        )
                    )
                }
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
    ) {
        CustomBackButton {
            onBackClicked()
        }
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "Hello! Register to get started",
            style = TextStyle(
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.15.sp,
                fontFamily = FontFamily.SansSerif
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomEditText(
            value = state.email,
            onValueChanged = onEmailChange,
            isPassword = false,
            hint = "Enter your email",
            modifier = Modifier.fillMaxWidth()
        )
        CustomEditText(
            value = state.name,
            onValueChanged = onNameChange,
            isPassword = false,
            hint = "Enter your name",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        CustomEditText(
            value = state.password,
            onValueChanged = onPasswordChange,
            hint = "Enter your password",
            isPassword = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { onRegisterClicked(state.email, state.password, state.name) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E232C),
                contentColor = Color.White
            )
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(color = Color(0xFF35C2C1), strokeWidth = 2.dp)
            } else {
                Text(text = "Register")
            }
        }

    }

}

