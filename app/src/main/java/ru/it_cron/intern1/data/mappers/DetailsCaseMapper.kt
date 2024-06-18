package ru.it_cron.intern1.data.mappers

import android.graphics.Color
import ru.it_cron.intern1.data.network.dto.case_dto.DataDetailsCaseDto
import ru.it_cron.intern1.data.network.dto.case_dto.PlatformsDto
import ru.it_cron.intern1.data.network.dto.case_dto.TechnologiesDto
import ru.it_cron.intern1.domain.model.cases.CaseDetails

private const val EMPTY = ""

fun DataDetailsCaseDto.dataDetailsCaseDtoToCaseDetails(): CaseDetails {
    return CaseDetails(
        id = id,
        title = title,
        images = images ?: emptyList(),
        task = task ?: EMPTY,
        technologies = technologies?.mapTechnologiesToString() ?: emptyList(),
        platforms = platforms?.mapPlatformsToString() ?: emptyList(),
        featureTitle = featureTitle ?: EMPTY,
        featuresDone = featuresDone ?: emptyList(),
        caseColorId = caseColor.convertToColor(),
        iOSUrl = iOSUrl ?: EMPTY,
        androidUrl = androidUrl ?: EMPTY,
        webUrl = webUrl ?: EMPTY,
        isColorWhite = caseColor.isColorWhite()
    )
}

fun List<TechnologiesDto>.mapTechnologiesToString() = map { it.name.transform() }

fun List<PlatformsDto>.mapPlatformsToString() = map { it.name }

fun String.convertToColor() = Color.parseColor("#FF$this")

fun String.isColorWhite() = this[0].uppercaseChar() == 'F'
fun String.transform() = replace("(", "\u2013 ").replace(")", "")
