package com.xera.sanadqrreader.domain.usecases

import com.xera.sanadqrreader.domain.repository.ScannerRepository
import javax.inject.Inject

class GetOutTime @Inject constructor(
    private val scannerRepository: ScannerRepository
)  {

    suspend operator fun invoke(qrCode: String): String {
        return scannerRepository.getOutTimeForProductLocal(qrCode)
    }
}