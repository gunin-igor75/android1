package ru.it_cron.android1.data.mappers

import ru.it_cron.android1.data.network.dto.cases_dto.DataCasesDto
import ru.it_cron.android1.domain.model.cases.Case


fun DataCasesDto.dataCaseDtoToCase(): Case {
    return Case(
        id = id,
        image = image,
        title = title,
        filters = filters.filtersDtoToFilters()
    )
}

fun List<DataCasesDto>.dataDtoCasesToCases() =
    map { it.dataCaseDtoToCase() }


