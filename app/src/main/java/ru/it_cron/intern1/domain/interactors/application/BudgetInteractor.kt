package ru.it_cron.intern1.domain.interactors.application

import ru.it_cron.intern1.domain.usecases.application.budgets.ClearAllBudgetUseCase
import ru.it_cron.intern1.domain.usecases.application.budgets.GetBudgetsItemUseCase
import ru.it_cron.intern1.domain.usecases.application.budgets.GetSelectedBudgetsUseCase
import ru.it_cron.intern1.domain.usecases.application.budgets.IsNotEmptySelectedBudgetUseCase
import ru.it_cron.intern1.domain.usecases.application.budgets.ToggleBudgetsUseCase

class BudgetInteractor(
    val items: GetBudgetsItemUseCase,
    val selectedItems: GetSelectedBudgetsUseCase,
    val toggle: ToggleBudgetsUseCase,
    val clearAll: ClearAllBudgetUseCase,
    val isNotEmpty: IsNotEmptySelectedBudgetUseCase,
)