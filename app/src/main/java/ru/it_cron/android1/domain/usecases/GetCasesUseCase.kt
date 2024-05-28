package ru.it_cron.android1.domain.usecases

import ru.it_cron.android1.domain.model.cases.Case
import ru.it_cron.android1.domain.repository.CasesRepository

class GetCasesUseCase(
    private val repository: CasesRepository<List<Case>>,
) {
    suspend operator fun invoke() = repository.getCases()
}