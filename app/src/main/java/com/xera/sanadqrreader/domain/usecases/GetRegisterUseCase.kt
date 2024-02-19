package com.xera.sanadqrreader.domain.usecases

import com.xera.sanadqrreader.domain.models.AuthEntity
import com.xera.sanadqrreader.domain.repository.ScannerRepository
import javax.inject.Inject

class GetRegisterUseCase @Inject constructor(
    private val repository: ScannerRepository
) {
    suspend operator fun invoke(email: String, name: String, password: String):AuthEntity{
     val token = repository.register(email, name, password).message
        repository.saveUserToken(token)
        return repository.register(email, name, password)
    }

}