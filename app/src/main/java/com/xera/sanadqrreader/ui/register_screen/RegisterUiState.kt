package com.xera.sanadqrreader.ui.register_screen

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val isSuccess: Boolean = false
)
