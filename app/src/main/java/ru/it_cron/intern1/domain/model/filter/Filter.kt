package ru.it_cron.intern1.domain.model.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    val id: String,
    val name: String,
) : Parcelable
