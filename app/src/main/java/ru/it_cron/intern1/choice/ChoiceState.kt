package ru.it_cron.intern1.choice

interface ChoiceState<T> {

    fun isChecked(item: T): Boolean

    fun isEmpty(): Boolean
}