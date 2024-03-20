package com.xera.sanadqrreader.data.repository

import android.util.Log
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.xera.sanadqrreader.data.repository.data_sources.LocalDataSource
import com.xera.sanadqrreader.data.repository.data_sources.RemoteDataSource
import com.xera.sanadqrreader.data.repository.data_sources.SharedPreferenceDataSource
import com.xera.sanadqrreader.data.repository.mappers.toEntity
import com.xera.sanadqrreader.data.repository.mappers.toResource
import com.xera.sanadqrreader.data.repository.mappers.toResourceUpdate
import com.xera.sanadqrreader.data.repository.resources.auth_resource.LoginResource
import com.xera.sanadqrreader.data.repository.resources.auth_resource.RegisterResource
import com.xera.sanadqrreader.data.repository.resources.in_stock_resource.AddProductInStockResource
import com.xera.sanadqrreader.domain.models.AuthEntity
import com.xera.sanadqrreader.domain.models.InStockEntity
import com.xera.sanadqrreader.domain.models.OutStockProductEntity
import com.xera.sanadqrreader.domain.models.ProductHistoryEntity
import com.xera.sanadqrreader.domain.repository.ScannerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.sql.Time
import java.time.Instant
import javax.inject.Inject

class ScannerRepositoryImpl @Inject constructor(
    private val scanner: GmsBarcodeScanner,
    private val moduleInstallClient: ModuleInstallClient,
    private val moduleInstallClientRequest: ModuleInstallRequest,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : ScannerRepository {

    private val scanningEnabled = MutableStateFlow(true)

    override fun startScanning(
        saveFunction: suspend (String?, String?, String, String) -> Unit,
        to: String,
        from: String
    ): Flow<String?> {
        return callbackFlow {
            val time = Time.from(Instant.now())
            try {
                moduleInstallClient
                    .installModules(moduleInstallClientRequest)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            fun startNewScan() {
                                if (scanningEnabled.value) {
                                    scanner.startScan()
                                        .addOnSuccessListener { barCode ->
                                            launch(Dispatchers.IO) {
                                                Log.i(
                                                    "Darkness",
                                                    "startScanning: ${barCode.rawValue}"
                                                )
                                                send(barCode.rawValue)
                                                barCode.rawValue?.let {
                                                    saveFunction(
                                                        it,
                                                        time.toString(), to, from
                                                    )
                                                }
                                                send("Scanning completed")
                                                startNewScan()
                                            }
                                        }
                                }
                            }

                            scanner.startScan()
                                .addOnSuccessListener { barCode ->
                                    launch(Dispatchers.IO) {
                                        Log.i("Darkness", "startScanning: ${barCode.rawValue}")
                                        send(barCode.rawValue)
                                        barCode.rawValue?.let {
                                            saveFunction(
                                                it,
                                                time.toString(),
                                                to,
                                                from
                                            )
                                        }
                                        send("Scanning completed")

                                        startNewScan()
                                    }
                                }
                                .addOnFailureListener {
                                    it.printStackTrace()

                                    startNewScan()
                                }
                        } else {
                            Log.i("Darkness", "startScanning: ${task.exception}")
                            task.exception?.printStackTrace()
                        }
                    }
                    .addOnCanceledListener {
                        scanningEnabled.value = false
                    }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            awaitClose { }
        }
    }

    override suspend fun saveInStockProductRemote(
        qrCode: String,
        getInTime: String,
        getOutTime: String,
        to: String,
        from: String,
        status: String
    ) {
        remoteDataSource.addProductInStock(addProductInStockResource = AddProductInStockResource(qrCode = qrCode, getInTime = getInTime, getOutTime = getOutTime, to =  to, from =  from, status =  status))
    }

    override suspend fun saveInStockProductLocal(
        qrCode: String,
        getInTime: String,
        getOutTime: String,
        to: String,
        from: String,
        status: String
    ) {
        if (!localDataSource.isProductInStock(qrCode)){
            localDataSource.saveInStockProduct(qrCode, getInTime, to, from, status, getOutTime)
        }
    }


    override suspend fun updateProductStatusLocal(qrCode: String, getOutTime: String) {
        localDataSource.updateProductStatus(qrCode = qrCode, getOutTime =  getOutTime)
    }

    override suspend fun getAllInStockProductLocal(): List<InStockEntity> {
        return localDataSource.getAllInStockProducts().toEntity()
    }

    override suspend fun getAllOutOfStockProductsLocal(): List<OutStockProductEntity> {
        return localDataSource.getAllOutStockProducts().toEntity()
    }

    override suspend fun saveOutStockProductLocal(
        qrCode: String,
        getOutTime: String,
        to: String,
        from: String,
        status: String,
        getInTime: String
    ) {
        if (!localDataSource.isProductOutStock(qrCode)) {
            localDataSource.saveOutStockProduct(qrCode, getOutTime, to, from, "out",getInTime)
        }
    }

    override suspend fun deleteInStockProductLocal(qrCode: String) {
        localDataSource.deleteInStockProduct(qrCode)
    }

    override suspend fun isProductInStockLocal(qrCode: String): Boolean {
        return localDataSource.isProductInStock(qrCode)
    }

    override suspend fun isProductOutStockLocal(qrCode: String): Boolean {
        return localDataSource.isProductOutStock(qrCode)
    }

    override suspend fun deleteOutStockProductLocal(qrCode: String) {
        localDataSource.deleteOutStockProduct(qrCode)
    }

    override suspend fun getInTimeForProductLocal(qrCode: String): String {
        return localDataSource.getInTime(qrCode)
    }

    override suspend fun getOutTimeForProductLocal(qrCode: String): String {
        return localDataSource.getOutTime(qrCode)
    }

    override suspend fun getProductHistoryLocal(qrCode: String): List<ProductHistoryEntity> {
        return localDataSource.getProductHistory(qrCode).toEntity()
    }

    override suspend fun insertProductHistoryLocal(
        qrCode: String,
        getInTime: String,
        getOutTime: String,
        to: String,
        from: String,
        status: String
    ) {
        localDataSource.saveProductHistory(qrCode, getInTime, getOutTime, to, from, status)
    }

    override suspend fun login(email: String, password: String): AuthEntity {
        return remoteDataSource.login(LoginResource( email, password)).toEntity()
    }

    override suspend fun register(email: String, name: String, password: String): AuthEntity {
        return remoteDataSource.register(RegisterResource(email, name, password)).toEntity()
    }

    override suspend fun addProductInStockRemote(inStockEntity: InStockEntity) {
        remoteDataSource.addProductInStock(addProductInStockResource = inStockEntity.toResource())
    }

    override suspend fun addProductOutStockRemote(outStockProductEntity: OutStockProductEntity) {
        remoteDataSource.addProductOutStock(outStockProductEntity.toResource())
    }

    override suspend fun addProductHistoryRemote(productHistoryEntity: ProductHistoryEntity) {
        remoteDataSource.addProductHistory(productHistoryEntity.toResource())
    }

    override suspend fun getAllInStockProductRemote(): List<InStockEntity> {
        return remoteDataSource.getAllInStock().toEntity()
    }

    override suspend fun getAllOutOfStockProductsRemote(): List<OutStockProductEntity> {
           return remoteDataSource.getAllOutStockProducts().toEntity()
    }

    override suspend fun getAllProductHistoryRemote(): List<ProductHistoryEntity> {
        return remoteDataSource.getAllProductHistory().toEntity()
    }

    override suspend fun updateInStockProductRemote(inStockEntity: InStockEntity) {
        remoteDataSource.updateInStockProduct(inStockEntity.toResourceUpdate())
    }

    override suspend fun updateOutStockProductRemote(outStockProductEntity: OutStockProductEntity) {
        remoteDataSource.updateOutStockProduct(outStockProductEntity.toResourceUpdate())
    }

    override suspend fun updateProductHistoryRemote(productHistoryEntity: ProductHistoryEntity) {
    remoteDataSource.updateProductHistory(productHistoryEntity.toResourceUpdate())
    }

    override suspend fun deleteInStockProductRemote(qrCode: String) {
        remoteDataSource.deleteInStockProduct(qrCode)
    }

    override suspend fun deleteOutStockProductRemote(qrCode: String) {
        remoteDataSource.deleteOutStockProduct(qrCode)
    }

    override suspend fun deleteProductHistoryRemote(id: Int) {
        remoteDataSource.deleteProductHistory(id)
    }

    override suspend fun saveUserToken(token: String) {
        sharedPreferenceDataSource.saveUserToken(token)
    }

    override suspend fun getUserToken(): String? {
       return sharedPreferenceDataSource.getUserToken()
    }

    override suspend fun clearUserToken() {
        sharedPreferenceDataSource.clearUserToken()
    }


}