package com.xera.sanadqrreader.domain.usecases

import com.xera.sanadqrreader.domain.models.AuthEntity
import com.xera.sanadqrreader.domain.repository.ScannerRepository
import javax.inject.Inject

class GetLoginUseCase @Inject constructor(
    private val repository: ScannerRepository
) {
    suspend operator fun invoke(email: String, password: String) :AuthEntity{
        repository.clearUserToken()
        val response = repository.login(email, password)
        repository.saveUserToken(response.message)
        return response
    }
}