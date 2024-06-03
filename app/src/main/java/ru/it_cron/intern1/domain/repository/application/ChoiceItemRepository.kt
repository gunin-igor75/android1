package ru.it_cron.intern1.domain.repository.application

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.it_cron.intern1.domain.model.app.AppItem

interface ChoiceItemRepository {
    fun itemsFlow(): Flow<List<AppItem>>
    fun toggle(resIdName: Int)
    fun getSelectedItems(): String
    fun clearAll()
    fun isNotEmpty(): StateFlow<Boolean>
}