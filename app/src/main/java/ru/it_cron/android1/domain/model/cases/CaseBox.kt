package ru.it_cron.android1.domain.model.cases

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CaseBox(
    val case: Case,
    val caseNext: Case?,
) : Parcelable