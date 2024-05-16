package ru.it_cron.android1.choice

interface ChoiceState<T> {

    fun isChecked(item: T): Boolean

    fun isEmpty(): Boolean
}