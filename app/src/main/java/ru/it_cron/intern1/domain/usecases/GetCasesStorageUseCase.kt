package ru.it_cron.intern1.domain.usecases

import ru.it_cron.intern1.domain.model.cases.Case
import ru.it_cron.intern1.domain.repository.CasesRepository

class GetCasesStorageUseCase(
    private val repository: CasesRepository<List<Case>>
) {
    operator fun invoke() = repository.getCasesStorage()
}