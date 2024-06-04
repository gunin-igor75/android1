package ru.it_cron.intern1.domain.model.app

sealed interface StateScreenApp {
    data object Success : StateScreenApp
    data object ErrorInternet : StateScreenApp
    data object Error : StateScreenApp
}