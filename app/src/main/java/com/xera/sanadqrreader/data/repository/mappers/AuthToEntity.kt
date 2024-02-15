package com.xera.sanadqrreader.data.repository.mappers

import com.xera.sanadqrreader.data.repository.resources.auth_resource.AuthResponseResource
import com.xera.sanadqrreader.domain.models.AuthEntity

fun AuthResponseResource.toEntity() = AuthEntity(
    message = message,
    successful = successful,
)