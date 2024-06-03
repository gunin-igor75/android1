package ru.it_cron.intern1.data.repository.application

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import ru.it_cron.intern1.R
import ru.it_cron.intern1.domain.model.app.AppItem
import ru.it_cron.intern1.domain.repository.application.ChoiceItemRepository

class AreaActivityRepositoryImpl(
    private val context: Context,
) : ChoiceItemRepository {

    private val _areaActivity: MutableList<AppItem> = mutableListOf(
        AppItem.Header(R.string.how_to_know),
        AppItem.App(R.string.social_network),
        AppItem.App(R.string.recommendations),
        AppItem.App(R.string.work),
        AppItem.App(R.string.ratings),
        AppItem.App(R.string.newsletter),
        AppItem.App(R.string.advertising)
    )

    private val areaActivity: List<AppItem>
        get() = _areaActivity.toList()

    private val eventChange = MutableSharedFlow<Unit>(replay = 1)

    private var resNamIdChecked = NOTHING_VALUE

    private val _resNamIdChecked: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override fun itemsFlow(): Flow<List<AppItem>> = flow {
        eventChange.onStart { emit(Unit) }
            .collect {
                _areaActivity.replaceAll { item ->
                    when (item) {
                        is AppItem.App -> {
                            if (isChecked(item.getNames())) {
                                item.copy(isEnabled = true)
                            } else {
                                item.copy(isEnabled = false)
                            }
                        }

                        is AppItem.Header -> {
                            item
                        }
                    }
                }
                _resNamIdChecked.value = resNamIdChecked != NOTHING_VALUE
                emit(areaActivity)
            }
    }

    override fun toggle(resIdName: Int) {
        if (isChecked(resIdName)) {
            clear(resIdName)
        } else {
            check(resIdName)
        }
    }

    override fun getSelectedItems(): String {
        return context.getString(resNamIdChecked)
    }

    override fun clearAll() {
        resNamIdChecked = NOTHING_VALUE
    }

    override fun isNotEmpty(): StateFlow<Boolean> {
        return _resNamIdChecked.asStateFlow()
    }

    private fun check(resIdName: Int) {
        if (isChecked(resIdName)) {
            return
        } else {
            resNamIdChecked = resIdName
        }
        notifyUpdate()
    }

    private fun clear(resIdName: Int) {
        if (isChecked(resIdName)) {
            resNamIdChecked = NOTHING_VALUE
        } else {
            return
        }
        notifyUpdate()
    }

    private fun isChecked(resIdName: Int): Boolean {
        return resNamIdChecked == resIdName
    }

    private fun notifyUpdate() {
        eventChange.tryEmit(Unit)
    }

    private companion object {
        const val NOTHING_VALUE = -1
    }
}