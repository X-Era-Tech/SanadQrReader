package com.xera.sanadqrreader.domain.usecases

import com.xera.sanadqrreader.domain.repository.ScannerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScannerForOutOfStockProductsUseCase @Inject constructor(
    private val scannerRepository: ScannerRepository
) {
    suspend operator fun invoke(to: String, from: String): Flow<String?> {
        return scannerRepository.startScanning(
            { qrCode, time, toWarehouse, fromWarehouse ->
                scannerRepository.updateProductStatus(qrCode ?: "Not Correct", time ?: "?")
                val getInTime = scannerRepository.getInTimeForProduct(qrCode ?: "Not Correct")
                scannerRepository.insertProductHistory(
                    qrCode ?: "Not Correct",
                    getInTime = "",
                    to = toWarehouse,
                    from = fromWarehouse,
                    status = "Out",
                    getOutTime = time ?: "?"
                )
                    scannerRepository.saveOutStockProduct(
                    qrCode = qrCode ?: "Not Correct",
                    getOutTime = time ?: "?",
                    to = toWarehouse,
                    from = fromWarehouse,
                    status = "Out",
                    getInTime = getInTime
                )
            },
            to = to, from = from
        )
    }
}