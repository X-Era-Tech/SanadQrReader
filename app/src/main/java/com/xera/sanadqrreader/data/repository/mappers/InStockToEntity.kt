package com.xera.sanadqrreader.data.repository.mappers

import com.xera.sanadqrreader.data.repository.entities.InStockProductsDto
import com.xera.sanadqrreader.data.repository.resources.in_stock_resource.AddProductInStockResource
import com.xera.sanadqrreader.data.repository.resources.in_stock_resource.GetAllInStockResource
import com.xera.sanadqrreader.data.repository.resources.in_stock_resource.GetAllInStockResourceItem
import com.xera.sanadqrreader.data.repository.resources.in_stock_resource.UpdateInStockProductResource
import com.xera.sanadqrreader.domain.models.InStockEntity


fun List<InStockProductsDto>.toEntity() :List<InStockEntity> {
    return map { it.toEntity() }
}
private fun InStockProductsDto.toEntity() = InStockEntity(
    qrCode = qrCode,
    getInTime = getInTime,
    getOutTime = getOutTime,
    to = to,
    from = from,
    status = status
)
fun GetAllInStockResource.toEntity() = map { it.toEntity() }
private fun GetAllInStockResourceItem.toEntity() = InStockEntity(
    qrCode = qrCode,
    getInTime = getInTime,
    getOutTime = getOutTime,
    to = to,
    from = from,
    status = status
)

fun InStockEntity.toResource() = AddProductInStockResource(
    qrCode = qrCode ,
    getInTime = getInTime ?: "",
    getOutTime = getOutTime?: "",
    to = to?: "",
    from = from?: "",
    status = status?: ""
)

fun InStockEntity.toResourceUpdate() = UpdateInStockProductResource(
    qrCode = qrCode,
    getInTime = getInTime ?: "",
    getOutTime = getOutTime?: "",
    to = to?: "",
    from = from?: "",
    status = status?: ""
)