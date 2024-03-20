package com.xera.sanadqrreader.ui.register_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xera.sanadqrreader.domain.usecases.GetRegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: GetRegisterUseCase
): ViewModel(){

    private val _state = MutableStateFlow(RegisterUiState())
    val state = _state.asStateFlow()


    fun onEmailChange(email: String) {
        _state.update {
            it.copy(
                email = email
            )
        }
    }

    fun onPasswordChange(password: String) {
       _state.update {
           it.copy(
               password = password
           )
       }
    }

    fun onNameChange(name: String) {
        _state.update {
            it.copy(
                name = name
            )
        }
    }

    fun register(email: String, password: String, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            try {
                val response = registerUseCase.invoke(email, password, name)
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
                        error = e.message ?: "Try Again"
                    )
                }
            }
        }
    }

}