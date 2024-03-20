package com.xera.sanadqrreader.domain.usecases

import com.xera.sanadqrreader.domain.models.InStockEntity
import com.xera.sanadqrreader.domain.models.ProductHistoryEntity
import com.xera.sanadqrreader.domain.repository.ScannerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScannerForInStockProductsUseCase @Inject constructor(
    private val scannerRepository: ScannerRepository
) {
    suspend operator fun invoke(to: String, from: String): Flow<String?> {

        return scannerRepository.startScanning({ code, time, toWarehouse, fromWarehouse ->
            val getOutTime = scannerRepository.getOutTimeForProductLocal(code ?: "Not Correct")
            scannerRepository.insertProductHistoryLocal(
                code ?: "Not Correct",
                getInTime = time ?: "?",
                to = toWarehouse,
                from = fromWarehouse,
                status = "in",
                getOutTime = ""
            )
            scannerRepository.saveInStockProductLocal(
                qrCode = code ?: "Not Correct",
                getInTime = time ?: "",
                getOutTime = getOutTime,
                to = toWarehouse,
                from = fromWarehouse,
                status = "in"
            )
            scannerRepository.addProductHistoryRemote(
                ProductHistoryEntity(
                    qrCode = code ?: "Not Correct",
                    getInTime = time ?: "?",
                    to = toWarehouse,
                    from = fromWarehouse,
                    status = "in",
                    getOutTime = ""
                )
            )
            scannerRepository.addProductInStockRemote(
                InStockEntity(
                    qrCode = code ?: "Not Correct",
                    getInTime = time ?: "?",
                    to = toWarehouse,
                    from = fromWarehouse,
                    status = "in",
                    getOutTime = getOutTime ?: "Still in stock"
                )
            )
        }, to = to, from = from)


    }
}