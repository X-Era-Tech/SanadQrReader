package com.xera.sanadqrreader.data.repository.mappers

import com.xera.sanadqrreader.data.repository.entities.OutStockProductsDto
import com.xera.sanadqrreader.data.repository.resources.out_stock_resource.AddOutStockProductResource
import com.xera.sanadqrreader.data.repository.resources.out_stock_resource.GetAllOutStockResource
import com.xera.sanadqrreader.data.repository.resources.out_stock_resource.GetAllOutStockResourceItem
import com.xera.sanadqrreader.data.repository.resources.out_stock_resource.UpdateOutStockProductResource
import com.xera.sanadqrreader.domain.models.OutStockProductEntity

fun List<OutStockProductsDto>.toEntity(): List<OutStockProductEntity> {
    return map { it.toEntity() }
}

private fun OutStockProductsDto.toEntity() = OutStockProductEntity(
    qrCode = qrCode,
    getInTime = getInTime,
    getOutTime = getOutTime,
    to = to,
    from = from,
    status = status
)


private fun GetAllOutStockResourceItem.toEntity() = OutStockProductEntity(
    qrCode = qrCode,
    getInTime = getInTime,
    getOutTime = getOutTime,
    to = to,
    from = from,
    status = status
)

fun GetAllOutStockResource.toEntity() = map { it.toEntity() }

fun OutStockProductEntity.toResource() = AddOutStockProductResource(
    qrCode = qrCode,
    getInTime = getInTime?:"",
    getOutTime = getOutTime ?: "",
    to = to ?: "",
    from = from ?: "",
    status = status ?: ""
)

fun OutStockProductEntity.toResourceUpdate() = UpdateOutStockProductResource(
    qrCode = qrCode,
    getInTime = getInTime?:"",
    getOutTime = getOutTime ?: "",
    to = to ?: "",
    from = from ?: "",
    status = status ?: ""
)