package com.xera.sanadqrreader.data.repository.mappers

import com.xera.sanadqrreader.data.repository.entities.ProductHistoryDto
import com.xera.sanadqrreader.data.repository.resources.product_history_resource.AddProductHistoryResource
import com.xera.sanadqrreader.data.repository.resources.product_history_resource.GetAllProductHistoryResource
import com.xera.sanadqrreader.data.repository.resources.product_history_resource.GetAllProductHistoryResourceItem
import com.xera.sanadqrreader.data.repository.resources.product_history_resource.UpdateProductHistoryResource
import com.xera.sanadqrreader.domain.models.ProductHistoryEntity

fun List<ProductHistoryDto>.toEntity():List<ProductHistoryEntity>{
    return map { it.toEntity() }
}

private fun ProductHistoryDto.toEntity() = ProductHistoryEntity(
    id = id,
    qrCode = qrCode,
    getInTime = getInTime,
    getOutTime = getOutTime,
    to = to,
    from = from,
    status = status
)

fun GetAllProductHistoryResource.toEntity() = map { it.toEntity() }

private fun GetAllProductHistoryResourceItem.toEntity() = ProductHistoryEntity(
    qrCode = qrCode,
    getInTime = getInTime,
    getOutTime = getOutTime,
    to = to,
    from = from,
    status = status
)

fun ProductHistoryEntity.toResource() = AddProductHistoryResource(
    id = id ?: 0,
    qrCode = qrCode,
    getInTime = getInTime ?: "",
    getOutTime = getOutTime ?: "",
    to = to ?: "",
    from = from ?: "",
    status = status ?: ""
)

fun ProductHistoryEntity.toResourceUpdate() = UpdateProductHistoryResource(
    id = id ?: 0,
    qrCode = qrCode,
    getInTime = getInTime ?: "",
    getOutTime = getOutTime ?: "",
    to = to ?: "",
    from = from ?: "",
    status = status ?: ""
)

