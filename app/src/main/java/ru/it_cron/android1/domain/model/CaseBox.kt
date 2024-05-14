package ru.it_cron.android1.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.it_cron.android1.domain.model.Case

@Parcelize
data class CaseBox(
    val case: Case,
    val caseNext: Case?
) : Parcelable