package ru.it_cron.android1.domain.model.contacts

data class DayWeekItem(
    val day: TypeDay,
    val resId: Int,
    val isCurrent: Boolean,
)

enum class TypeDay {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    DAY_OF
}