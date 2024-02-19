package com.xera.sanadqrreader.domain.usecases

import com.xera.sanadqrreader.domain.repository.ScannerRepository
import javax.inject.Inject

class GetAllOutStockLocal @Inject constructor(
    private val repository: ScannerRepository
) {
    suspend operator fun invoke () = repository.getAllOutOfStockProductsLocal()
}