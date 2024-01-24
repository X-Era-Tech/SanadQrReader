package com.xera.sanadqrreader.ui.product_history

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xera.sanadqrreader.data.repository.entities.ProductHistory
import com.xera.sanadqrreader.domain.usecases.GetAllProductHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductHistoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductHistory: GetAllProductHistoryUseCase,
):ViewModel() {
    private val _state = MutableStateFlow(ProductHistoryUiState())
    val state = _state.asStateFlow()
    private val args:ProductHistoryArgs = ProductHistoryArgs(savedStateHandle)

    init {
        Log.i("ProductHistoryViewModel", "init: ${args.qrCode}")
        getAllProductHistory(args.qrCode)
    }

    private fun getAllProductHistory(qrCode: String) {

        viewModelScope.launch {
            val result = getProductHistory.invoke(qrCode).map { it.toUiState() }
            _state.value = state.value.copy(
                details = result,
            )
        }
    }


    private fun ProductHistory.toUiState(): History {
        return History(
            qrCode = this.qrCode,
            status = this.status,
            getInTime = this.getInTime,
            getOutTime = getOutTime,
            from = this.from,
            to = this.to
        )
    }
}

