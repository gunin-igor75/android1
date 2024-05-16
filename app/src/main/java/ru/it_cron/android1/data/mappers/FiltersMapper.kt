package ru.it_cron.android1.data.mappers

import ru.it_cron.android1.data.network.dto.cases_dto.FilterDto
import ru.it_cron.android1.data.network.dto.filter_dto.DataFiltersDto
import ru.it_cron.android1.domain.model.Filter
import ru.it_cron.android1.domain.model.FiltersGroup

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