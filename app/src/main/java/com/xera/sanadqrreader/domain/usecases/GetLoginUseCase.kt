package com.xera.sanadqrreader.domain.usecases

import com.xera.sanadqrreader.domain.models.AuthEntity
import com.xera.sanadqrreader.domain.repository.ScannerRepository
import javax.inject.Inject

class GetLoginUseCase @Inject constructor(
    private val repository: ScannerRepository
) {
    suspend operator fun invoke(email: String, password: String) :AuthEntity{
        val token = repository.login(email, password).message
        repository.saveUserToken(token)
        return repository.login(email, password)
    }
}