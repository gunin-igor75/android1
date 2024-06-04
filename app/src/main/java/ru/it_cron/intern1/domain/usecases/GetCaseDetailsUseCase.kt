package ru.it_cron.intern1.domain.usecases

import ru.it_cron.intern1.domain.model.cases.CaseDetails
import ru.it_cron.intern1.domain.repository.CaseDetailsRepository

class GetCaseDetailsUseCase(
    private val repository: CaseDetailsRepository<CaseDetails>,
) {
    suspend operator fun invoke(caseId: String) = repository.getCase(caseId)
}