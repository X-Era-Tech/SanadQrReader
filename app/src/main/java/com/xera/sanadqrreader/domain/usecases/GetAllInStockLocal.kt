package com.xera.sanadqrreader.domain.usecases

import com.xera.sanadqrreader.domain.repository.ScannerRepository
import javax.inject.Inject

class GetAllInStockLocal @Inject constructor(
    private val repository: ScannerRepository
) {
    suspend operator fun invoke () = repository.getAllInStockProductLocal()
}