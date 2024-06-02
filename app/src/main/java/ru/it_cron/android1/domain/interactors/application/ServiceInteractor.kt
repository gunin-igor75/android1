package ru.it_cron.android1.domain.interactors.application

import ru.it_cron.android1.domain.usecases.application.services.ClearAllServiceUseCase
import ru.it_cron.android1.domain.usecases.application.services.GetSelectedServicesUseCase
import ru.it_cron.android1.domain.usecases.application.services.GetServicesItemUseCase
import ru.it_cron.android1.domain.usecases.application.services.ToggleServiceUseCase

class ServiceInteractor(
    val items: GetServicesItemUseCase,
    val selectedItems: GetSelectedServicesUseCase,
    val toggle: ToggleServiceUseCase,
    val clearAll: ClearAllServiceUseCase,
)