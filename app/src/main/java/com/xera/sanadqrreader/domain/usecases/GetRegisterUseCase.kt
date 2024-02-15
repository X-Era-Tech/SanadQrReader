package com.xera.sanadqrreader.domain.usecases

import com.xera.sanadqrreader.domain.repository.ScannerRepository
import javax.inject.Inject

class GetRegisterUseCase @Inject constructor(
    private val repository: ScannerRepository
) {
    suspend operator fun invoke(email: String, name: String, password: String) =
        repository.register(email, name, password)
}