package com.xera.sanadqrreader.domain.usecases

import com.xera.sanadqrreader.domain.repository.ScannerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScannerUseCase @Inject constructor(
    private val scannerRepository: ScannerRepository
) {
    suspend operator fun invoke(): Flow<String?> {
        return scannerRepository.startScanning()
    }
}