package ru.it_cron.android1.navigation

sealed class ScreenTag(
    val tag: String
) {
    data object CASE: ScreenTag(TAG_CASE)

    private companion object{
        const val TAG_CASE = "cases"
    }
}