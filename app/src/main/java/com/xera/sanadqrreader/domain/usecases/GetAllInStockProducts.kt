package com.xera.sanadqrreader.domain.usecases

import com.xera.sanadqrreader.domain.repository.ScannerRepository
import javax.inject.Inject

class GetAllInStockProducts @Inject constructor(
    private val scannerRepository: ScannerRepository
) {
    suspend operator fun invoke() = scannerRepository.getAllInStockProduct()

}
