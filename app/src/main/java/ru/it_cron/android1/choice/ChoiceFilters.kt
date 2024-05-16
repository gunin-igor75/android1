package ru.it_cron.android1.choice

import kotlinx.coroutines.flow.Flow
import java.util.HashSet

interface ChoiceFilters<T: Any> {

    fun toggle(item: T)

    fun check(item: T)

    fun clear(item: T)

    fun clearAll()

    fun listen(): Flow<ChoiceState<T>>

    fun isEnabled(): Boolean

    fun getEnabledId(): Set<String>

}