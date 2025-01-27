package ru.it_cron.intern1.domain.usecases

import ru.it_cron.intern1.domain.model.filter.FiltersGroup
import ru.it_cron.intern1.domain.repository.FilterRepository

class GetFiltersUseCase(
    private val repository: FilterRepository<List<FiltersGroup>>,
) {
    suspend operator fun invoke() = repository.getFilters()
}