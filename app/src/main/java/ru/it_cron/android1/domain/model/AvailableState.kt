package ru.it_cron.android1.domain.model

sealed interface AvailableState {

    data object Initial: AvailableState

    data class Error(
        val error: String
    ): AvailableState

    data class Success(
        val isAvailable: Boolean
    ): AvailableState
}