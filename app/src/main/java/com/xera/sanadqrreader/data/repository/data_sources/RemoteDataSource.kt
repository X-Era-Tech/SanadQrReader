package com.xera.sanadqrreader.data.repository.data_sources

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

interface RemoteDataSource {

    suspend fun login(loginResource: LoginResource): AuthResponseResource
    suspend fun register(registerResource: RegisterResource): AuthResponseResource

    //region in stock

    suspend fun addProductInStock(addProductInStockResource: AddProductInStockResource): DefaultResponseResource

    suspend fun getAllInStock(): GetAllInStockResource

    suspend fun updateInStockProduct(updateInStockProductResource: UpdateInStockProductResource): DefaultResponseResource

    suspend fun deleteInStockProduct(qrCode: String): DefaultResponseResource

    //endregion

    //region out stock

    suspend fun addProductOutStock(addOutStockProductResource: AddOutStockProductResource): DefaultResponseResource

    suspend fun getAllOutStockProducts(): GetAllOutStockResource

    suspend fun updateOutStockProduct(updateOutStockProductResource: UpdateOutStockProductResource): DefaultResponseResource

    suspend fun deleteOutStockProduct(qrCode: String):DefaultResponseResource

    //endregion


    //region product history

    suspend fun getAllProductHistory(): GetAllProductHistoryResource

    suspend fun addProductHistory(addProductHistoryResource: AddProductHistoryResource):DefaultResponseResource

    suspend fun updateProductHistory(updateProductHistoryResource: UpdateProductHistoryResource):DefaultResponseResource

    suspend fun deleteProductHistory(id:Int):DefaultResponseResource

    //endregion

}