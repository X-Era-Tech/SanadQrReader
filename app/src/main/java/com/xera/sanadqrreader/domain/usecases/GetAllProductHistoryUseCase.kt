package com.xera.sanadqrreader.domain.usecases

import com.xera.sanadqrreader.domain.repository.ScannerRepository
import javax.inject.Inject

class GetAllProductHistoryUseCase @Inject constructor(
    private val repository: ScannerRepository
){
    suspend operator fun invoke(qrCode:String) = repository.getProductHistory(qrCode)
}