package com.xera.sanadqrreader.ui.login_screen

data class LoginUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val error: String = "",
    val isLoggedIn: Boolean = false,
    val isSuccess: Boolean = false
)
