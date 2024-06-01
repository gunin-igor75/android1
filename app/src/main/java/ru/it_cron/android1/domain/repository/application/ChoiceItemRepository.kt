package ru.it_cron.android1.domain.repository.application

import kotlinx.coroutines.flow.Flow
import ru.it_cron.android1.domain.model.app.AppItem

interface ChoiceItemRepository {
    fun itemsFlow(): Flow<List<AppItem>>
    fun toggle(resIdName: Int)
    fun getSelectedItems(): String
    fun clearAll()
}