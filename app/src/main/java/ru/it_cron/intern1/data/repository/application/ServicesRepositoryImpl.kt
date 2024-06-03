package ru.it_cron.intern1.data.repository.application

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import ru.it_cron.intern1.R
import ru.it_cron.intern1.domain.model.app.AppItem
import ru.it_cron.intern1.domain.repository.application.ChoiceItemRepository

class ServicesRepositoryImpl(
    private val context: Context,
) : ChoiceItemRepository {

    private val _services: MutableList<AppItem> = mutableListOf(
        AppItem.Header(R.string.services),
        AppItem.App(R.string.ui_test),
        AppItem.App(R.string.design_app),
        AppItem.App(R.string.design_ui),
        AppItem.App(R.string.develop_web),
        AppItem.App(R.string.develop_mobile),
        AppItem.App(R.string.strategy),
        AppItem.App(R.string.creative),
        AppItem.App(R.string.analytics),
        AppItem.App(R.string.testings),
        AppItem.App(R.string.other)
    )

    private val services: List<AppItem>
        get() = _services.toList()

    private val eventChange = MutableSharedFlow<Unit>(replay = 1)

    private val setId = HashSet<Int>()

    override fun itemsFlow(): Flow<List<AppItem>> = flow {
        eventChange.onStart { emit(Unit) }.collect {
            _services.replaceAll { item ->
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
            emit(services)
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
        return setId.joinToString("\n") { context.getString(it) }
    }

    override fun clearAll() {
        setId.clear()
        notifyUpdate()
    }

    private fun check(resIdName: Int) {
        if (isChecked(resIdName)) {
            return
        } else {
            setId.add(resIdName)
        }
        notifyUpdate()
    }

    private fun clear(resIdName: Int) {
        if (isChecked(resIdName)) {
            setId.remove(resIdName)
        } else {
            return
        }
        notifyUpdate()
    }

    private fun isChecked(resIdName: Int): Boolean {
        return setId.contains(resIdName)
    }

    private fun notifyUpdate() {
        eventChange.tryEmit(Unit)
    }
}