package ru.it_cron.intern1.data.mappers

import ru.it_cron.intern1.data.network.dto.cases_dto.FilterDto
import ru.it_cron.intern1.data.network.dto.filter_dto.DataFiltersDto
import ru.it_cron.intern1.domain.model.filter.Filter
import ru.it_cron.intern1.domain.model.filter.FiltersGroup

fun FilterDto.filterDtoToFilter(): Filter {
    return Filter(
        id = id,
        name = name
    )
}

fun List<FilterDto>.filtersDtoToFilters() = map { it.filterDtoToFilter() }

fun DataFiltersDto.dataFilterDtoToFiltersGroup(): FiltersGroup {
    return FiltersGroup(
        id = id,
        name = name,
        filters = filters.filtersDtoToFilters()
    )
}

fun List<DataFiltersDto>.dataFiltersDtoToFiltersGroups() = map { it.dataFilterDtoToFiltersGroup() }