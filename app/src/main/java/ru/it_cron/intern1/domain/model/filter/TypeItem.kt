package ru.it_cron.intern1.domain.model.filter


sealed interface TypeItem {
    fun getIds(): String

    data class Header(
        val id: String,
        val name: String,
    ) : TypeItem {
        override fun getIds() = id
    }

    data class FilterItem(
        val id: String,
        val name: String,
        val isEnabled: Boolean,
    ) : TypeItem {
        override fun getIds() = id
    }
}