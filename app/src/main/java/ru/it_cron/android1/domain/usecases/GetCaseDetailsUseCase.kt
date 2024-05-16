package ru.it_cron.android1.domain.usecases

import ru.it_cron.android1.domain.model.CaseDetails
import ru.it_cron.android1.domain.repository.CaseDetailsRepository

class GetCaseDetailsUseCase(
    private val repository: CaseDetailsRepository<CaseDetails>
) {
    suspend operator fun invoke(caseId: String) = repository.getCase(caseId)
}