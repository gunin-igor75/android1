package ru.it_cron.intern1.domain.model.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContainerImage(
    val colorId: Int = 0,
    val images: List<String> = emptyList(),
) : Parcelable
