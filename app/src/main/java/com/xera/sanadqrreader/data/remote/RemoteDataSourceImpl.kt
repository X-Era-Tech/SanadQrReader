package com.xera.sanadqrreader.data.remote

import com.xera.sanadqrreader.data.remote.utils.NetworkException
import com.xera.sanadqrreader.data.repository.data_sources.RemoteDataSource
import com.xera.sanadqrreader.data.repository.resources.DefaultResponseResource
import com.xera.sanadqrreader.data.repository.resources.auth_resource.AuthResponseResource
import com.xera.sanadqrreader.data.repository.resources.auth_resource.LoginResource
import com.xera.sanadqrreader.data.repository.resources.auth_resource.RegisterResource
import com.xera.sanadqrreader.data.repository.resources.in_stock_resource.AddProductInStockResource
import com.xera.sanadqrreader.data.repository.resources.in_stock_resource.GetAllInStockResource
import com.xera.sanadqrreader.data.repository.resources.in_stock_resource.UpdateInStockProductResource
import com.xera.sanadqrreader.data.repository.resources.out_stock_resource.AddOutStockProductResource
import com.xera.sanadqrreader.data.repository.resources.out_stock_resource.GetAllOutStockResource
import com.xera.sanadqrreader.data.repository.resources.out_stock_resource.UpdateOutStockProductResource
import com.xera.sanadqrreader.data.repository.resources.product_history_resource.AddProductHistoryResource
import com.xera.sanadqrreader.data.repository.resources.product_history_resource.GetAllProductHistoryResource
import com.xera.sanadqrreader.data.repository.resources.product_history_resource.UpdateProductHistoryResource
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val xeraService: XeraService
) : RemoteDataSource {
    override suspend fun login(loginResource: LoginResource): AuthResponseResource {
        return tryToExecute { xeraService.login(loginResource) }
    }

    override suspend fun register(registerResource: RegisterResource): AuthResponseResource {
        return tryToExecute { xeraService.getRegister(registerResource) }
    }

    override suspend fun addProductInStock(addProductInStockResource: AddProductInStockResource): DefaultResponseResource {
        return tryToExecute { xeraService.addProductInStock(addProductInStockResource) }
    }

    override suspend fun getAllInStock(): GetAllInStockResource {
        return tryToExecute { xeraService.getAllInStock() }
    }

    override suspend fun updateInStockProduct(updateInStockProductResource: UpdateInStockProductResource): DefaultResponseResource {
        return tryToExecute { xeraService.updateInStockProduct(updateInStockProductResource) }
    }

    override suspend fun deleteInStockProduct(qrCode: String): DefaultResponseResource {
        return tryToExecute { xeraService.deleteInStockProduct(qrCode) }
    }

    override suspend fun addProductOutStock(addOutStockProductResource: AddOutStockProductResource): DefaultResponseResource {
        return tryToExecute { xeraService.addProductOutStock(addOutStockProductResource) }
    }

    override suspend fun getAllOutStockProducts(): GetAllOutStockResource {
        return tryToExecute { xeraService.getAllOutStockProducts() }
    }

    override suspend fun updateOutStockProduct(updateOutStockProductResource: UpdateOutStockProductResource): DefaultResponseResource {
        return tryToExecute { xeraService.updateOutStockProduct(updateOutStockProductResource) }
    }

    override suspend fun deleteOutStockProduct(qrCode: String): DefaultResponseResource {
        return tryToExecute { xeraService.deleteOutStockProduct(qrCode) }
    }

    override suspend fun getAllProductHistory(): GetAllProductHistoryResource {
        return tryToExecute { xeraService.getAllProductHistory() }
    }

    override suspend fun addProductHistory(addProductHistoryResource: AddProductHistoryResource): DefaultResponseResource {
       return tryToExecute { xeraService.addProductHistory(addProductHistoryResource) }
    }

    override suspend fun updateProductHistory(updateProductHistoryResource: UpdateProductHistoryResource): DefaultResponseResource {
        return tryToExecute { xeraService.updateProductHistory(updateProductHistoryResource) }
    }

    override suspend fun deleteProductHistory(id: Int): DefaultResponseResource {
        return tryToExecute { xeraService.deleteProductHistory(id) }
    }

    private suspend fun <T> tryToExecute(func: suspend () -> Response<T>): T {
        try {
            val response = func()
            if (response.isSuccessful) {
                return response.body() ?: throw NetworkException.NotFoundException()
            }
            throw when (response.code()) {
                404 -> NetworkException.NotFoundException()
                402 -> NetworkException.ApiKeyExpiredException()
                401 -> NetworkException.UnAuthorizedException()
                502 -> NetworkException.NoInternetException()
                500 -> NetworkException.InternalServerError()
                else -> IOException()
            }
        } catch (e: ConnectException) {
            throw NetworkException.NoInternetException()
        } catch (e: NetworkException.UnAuthorizedException) {
            // Handle the exception here. For example, you can show a user-friendly error message.
            // You can also return a default value or null, depending on your use case.
            return null as T
        }
    }
}