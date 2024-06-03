package ru.it_cron.intern1.domain.model.cases

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.it_cron.intern1.domain.model.filter.Filter

@Parcelize
data class Case(
    val id: String,
    val image: String,
    val title: String,
    val filters: List<Filter>,
) : Parcelable
