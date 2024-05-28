package ru.it_cron.android1.domain.model.app


sealed interface AppItem {
    fun getNames(): String

    data class Header(
        val name: String,
    ) : AppItem {
        override fun getNames() = name
    }

    data class App(
        val name: String,
        val isEnabled: Boolean = false,
    ) : AppItem {
        override fun getNames() = name
    }
}