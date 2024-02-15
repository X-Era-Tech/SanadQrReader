package com.xera.sanadqrreader.domain.usecases

import com.xera.sanadqrreader.domain.models.InStockEntity
import com.xera.sanadqrreader.domain.repository.ScannerRepository
import javax.inject.Inject

class GetAllInStockProducts @Inject constructor(
    private val scannerRepository: ScannerRepository
) {
    suspend operator fun invoke():List<InStockEntity>{
        return scannerRepository.getAllInStockProductRemote()
    }

}
