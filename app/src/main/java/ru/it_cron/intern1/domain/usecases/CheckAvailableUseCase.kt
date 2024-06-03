package ru.it_cron.intern1.domain.usecases

import ru.it_cron.intern1.domain.repository.AccessRepository

class CheckAvailableUseCase(
    private val repository: AccessRepository<Boolean>,
) {
    suspend operator fun invoke() = repository.checkAvailableCabinet()
}