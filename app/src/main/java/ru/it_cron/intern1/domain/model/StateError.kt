package ru.it_cron.intern1.domain.model

sealed interface StateError {
    data object ErrorInternet : StateError
    data class Error(
        val message: String,
    ) : StateError
}