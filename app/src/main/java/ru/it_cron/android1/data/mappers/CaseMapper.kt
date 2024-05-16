package ru.it_cron.android1.data.mappers

import ru.it_cron.android1.data.network.dto.DataCasesDto
import ru.it_cron.android1.domain.model.Case


fun DataCasesDto.dataCaseDtoToCase(): Case {
    return Case(
        id, image, title
    )
}

fun List<DataCasesDto>.dataDtoCasesToCases() =
    map { it.dataCaseDtoToCase() }