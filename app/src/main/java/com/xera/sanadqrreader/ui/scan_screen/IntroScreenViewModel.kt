package com.xera.sanadqrreader.ui.scan_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.xera.sanadqrreader.domain.usecases.GetLogOutUseCase
import com.xera.sanadqrreader.ui.login_screen.navigateToLoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class IntroScreenViewModel @Inject constructor(
    private val getLogOutUseCase: GetLogOutUseCase,
) : ViewModel() {

    fun logOut(navController: NavController) {
        viewModelScope.launch {
            getLogOutUseCase.invoke()
            navController.navigateToLoginScreen()
        }
    }
}