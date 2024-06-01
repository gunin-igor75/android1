package ru.it_cron.android1.domain.usecases.application

import ru.it_cron.android1.data.model.RequestApp
import ru.it_cron.android1.domain.repository.application.AppRepository

class SendAppUseCase(
    private val repository: AppRepository,
) {
    suspend operator fun invoke(requestApp: RequestApp) =
        repository.sendApp(requestApp)
}