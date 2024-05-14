package ru.it_cron.android1.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContainerImage(
    val colorId: Int = 0,
    val images: List<String> = emptyList()
): Parcelable
