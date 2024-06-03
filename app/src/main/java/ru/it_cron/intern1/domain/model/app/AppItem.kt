package ru.it_cron.intern1.domain.model.app


sealed interface AppItem {
    fun getNames(): Int

    data class Header(
        val resIdName: Int,
    ) : AppItem {
        override fun getNames() = resIdName
    }

    data class App(
        val resIdName: Int,
        val isEnabled: Boolean = false,
    ) : AppItem {
        override fun getNames() = resIdName
    }
}