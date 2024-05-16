package ru.it_cron.android1.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    val id: String,
    val name: String,
) : Parcelable
