package ru.it_cron.android1.domain.usecases

import ru.it_cron.android1.domain.model.FiltersGroup
import ru.it_cron.android1.domain.repository.FilterRepository

class GetFiltersUseCase(
    private val repository: FilterRepository<List<FiltersGroup>>,
) {
    suspend operator fun invoke() = repository.getFilters()
}