package com.xera.sanadqrreader.domain.usecases

import com.xera.sanadqrreader.domain.repository.ScannerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScannerForInStockProductsUseCase @Inject constructor(
    private val scannerRepository: ScannerRepository
) {
    suspend operator fun invoke(to: String, from: String): Flow<String?> {

        return scannerRepository.startScanning({ code, time, toWarehouse, fromWarehouse ->
            val getOutTime = scannerRepository.getOutTimeForProduct(code ?: "Not Correct")
            scannerRepository.insertProductHistory(
                code ?: "Not Correct",
                getInTime = time ?: "?",
                to = toWarehouse,
                from = fromWarehouse,
                status = "in",
                getOutTime = ""
            )
            scannerRepository.saveInStockProduct(
                qrCode = code ?: "Not Correct",
                getInTime = time ?: "?",
                to = toWarehouse,
                from = fromWarehouse,
                status = "in",
                getOutTime = getOutTime
            )
        }, to = to, from = from)

    }
}