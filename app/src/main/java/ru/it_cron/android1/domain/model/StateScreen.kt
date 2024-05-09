package ru.it_cron.android1.domain.model

sealed interface StateScreen<T> {
    class Initial<T> : StateScreen<T>
    class Loading<T>: StateScreen<T>
    data class Success <T>(
        val value: T,
    ) : StateScreen<T>
}