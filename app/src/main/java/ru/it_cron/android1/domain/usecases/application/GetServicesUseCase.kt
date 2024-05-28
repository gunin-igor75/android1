package ru.it_cron.android1.domain.usecases.application

import ru.it_cron.android1.domain.repository.AppRepository

class GetServicesUseCase(
    private val repository: AppRepository
) {
    operator fun invoke() = repository.getServices()
}