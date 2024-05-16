package ru.it_cron.android1.data.model

sealed interface DataResult<T> {

    data class Success<T>(
        val value: T,
    ) : DataResult<T>

    data class Error<T>(
        val error: String,
    ) : DataResult<T>

    class ErrorInternet<T> : DataResult<T>
}
