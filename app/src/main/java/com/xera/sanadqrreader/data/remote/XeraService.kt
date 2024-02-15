package com.xera.sanadqrreader.data.remote

import com.xera.sanadqrreader.data.repository.resources.auth_resource.LoginResource
import com.xera.sanadqrreader.data.repository.resources.auth_resource.AuthResponseResource
import com.xera.sanadqrreader.data.repository.resources.auth_resource.RegisterResource
import com.xera.sanadqrreader.data.repository.resources.in_stock_resource.AddProductInStockResource
import com.xera.sanadqrreader.data.repository.resources.DefaultResponseResource
import com.xera.sanadqrreader.data.repository.resources.in_stock_resource.GetAllInStockResource
import com.xera.sanadqrreader.data.repository.resources.in_stock_resource.UpdateInStockProductResource
import com.xera.sanadqrreader.data.repository.resources.out_stock_resource.AddOutStockProductResource
import com.xera.sanadqrreader.data.repository.resources.out_stock_resource.GetAllOutStockResource
import com.xera.sanadqrreader.data.repository.resources.out_stock_resource.UpdateOutStockProductResource
import com.xera.sanadqrreader.data.repository.resources.product_history_resource.AddProductHistoryResource
import com.xera.sanadqrreader.data.repository.resources.product_history_resource.GetAllProductHistoryResource
import com.xera.sanadqrreader.data.repository.resources.product_history_resource.UpdateProductHistoryResource
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface XeraService {

    //region authentication
    @POST("users/login")
    suspend fun login(
        @Body loginResource: LoginResource
    ):Response<AuthResponseResource>

    @POST("users/register")
    suspend fun getRegister(
        @Body registerResource: RegisterResource
    ):Response<AuthResponseResource>

    //endregion

    //region in stock
    @POST("inStock/add")
    suspend fun addProductInStock(
        @Body addProductInStockResource: AddProductInStockResource
    ):Response<DefaultResponseResource>

    @GET("inStock")
    suspend fun getAllInStock():Response<GetAllInStockResource>

    @POST("inStock/update")
    suspend fun updateInStockProduct(
        @Body updateInStockProductResource: UpdateInStockProductResource
    ):Response<DefaultResponseResource>

    @DELETE("inStock/delete")
    suspend fun deleteInStockProduct(
        @Query ("qrCode") qrCode:String
    ):Response<DefaultResponseResource>

    //endregion

    //region out stock

    @POST("outStock/addOut")
    suspend fun addProductOutStock(
        @Body addOutStockProductResource: AddOutStockProductResource
    ):Response<DefaultResponseResource>

    @GET("outStock")
    suspend fun getAllOutStockProducts():Response<GetAllOutStockResource>

    @POST("outStock/updateOut")
    suspend fun updateOutStockProduct(
        @Body updateOutStockProductResource: UpdateOutStockProductResource
    ):Response<DefaultResponseResource>

    @DELETE("outStock/deleteOut")
    suspend fun deleteOutStockProduct(
        @Query("qrCode") qrCode: String
    ):Response<DefaultResponseResource>

    //endregion

    //region product history

    @GET("productHistory")
    suspend fun getAllProductHistory():Response<GetAllProductHistoryResource>

    @POST("productHistory/addHistory")
    suspend fun addProductHistory(
        @Body addProductHistoryResource: AddProductHistoryResource
    ):Response<DefaultResponseResource>

    @POST("productHistory/updateHistory")
    suspend fun updateProductHistory(
        @Body updateProductHistoryResource: UpdateProductHistoryResource
    ):Response<DefaultResponseResource>

    @DELETE("productHistory/deleteHistory")
    suspend fun deleteProductHistory(
        @Query("id") id:Int
    ):Response<DefaultResponseResource>

    //endregion
}