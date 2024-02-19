package com.xera.sanadqrreader.ui.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xera.sanadqrreader.domain.usecases.GetLoginUseCase
import com.xera.sanadqrreader.domain.usecases.GetUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: GetLoginUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    init {
        checkLoginStatus()
    }

    fun onEmailChange(email: String) {
        _state.value = state.value.copy(
            email = email
        )
    }

    fun onPasswordChange(password: String) {
        _state.value = state.value.copy(
            password = password
        )
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(isLoading = true)
            }
            try {
                val response = loginUseCase.invoke(email, password)
                _state.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = response.successful,
                        error = if (response.successful) "" else response.message
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = false,
                        error = e.message ?: "Try Again"
                    )
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun checkLoginStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val token = getUserTokenUseCase.invoke()
            _state.update { it.copy(isLoggedIn = token != null) }
        }
    }
}