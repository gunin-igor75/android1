package ru.it_cron.android1.domain.model

sealed interface StateApp<T> {

    class Initial<T> : StateApp<T>

    class Loading<T>: StateApp<T>
    class ErrorInternet<T> : StateApp<T>

    data class Error<T>(
        val error: String,
    ) : StateApp<T>

    data class Success <T>(
        val value: T,
    ) : StateApp<T>
}