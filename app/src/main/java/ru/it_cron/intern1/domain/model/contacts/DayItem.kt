package ru.it_cron.intern1.domain.model.contacts

data class DayItem(
    val typeDay: TypeDay,
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