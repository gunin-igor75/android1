package ru.it_cron.intern1.domain.interactors.application

import ru.it_cron.intern1.domain.usecases.application.areaActivity.ClearAllAreaActivityUseCase
import ru.it_cron.intern1.domain.usecases.application.areaActivity.GetAreaActivityItemUseCase
import ru.it_cron.intern1.domain.usecases.application.areaActivity.GetSelectedAreaActivityUseCase
import ru.it_cron.intern1.domain.usecases.application.areaActivity.ToggleAreaActivityUseCase

class AreaActivityInteractor(
    val items: GetAreaActivityItemUseCase,
    val selectedItems: GetSelectedAreaActivityUseCase,
    val toggle: ToggleAreaActivityUseCase,
    val clearAll: ClearAllAreaActivityUseCase,
)