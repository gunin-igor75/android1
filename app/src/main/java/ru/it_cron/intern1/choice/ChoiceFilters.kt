package ru.it_cron.intern1.choice

import kotlinx.coroutines.flow.Flow

interface ChoiceFilters<T : Any> {

    fun toggle(item: T)

    fun check(item: T)

    fun clear(item: T)

    fun clearAll()

    fun stateIn(): Flow<ChoiceState<T>>

    fun isEnabled(): Boolean

    fun getEnabledId(): Set<String>

    fun isEmpty(): Boolean

}