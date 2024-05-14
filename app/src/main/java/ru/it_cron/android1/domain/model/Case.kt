package ru.it_cron.android1.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Case(
    val id: String,
    val image: String,
    val title: String,
): Parcelable
