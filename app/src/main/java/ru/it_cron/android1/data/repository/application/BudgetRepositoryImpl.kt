package ru.it_cron.android1.data.repository.application

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import ru.it_cron.android1.R
import ru.it_cron.android1.domain.model.app.AppItem
import ru.it_cron.android1.domain.repository.application.ChoiceItemRepository

class BudgetRepositoryImpl(
    private val context: Context,
) : ChoiceItemRepository {

    private val _budgets: MutableList<AppItem> = mutableListOf(
        AppItem.Header(R.string.budget),
        AppItem.App(R.string.budget_less),
        AppItem.App(R.string.budget_05_1),
        AppItem.App(R.string.budget_1_3),
        AppItem.App(R.string.budget_5_10),
        AppItem.App(R.string.budget_10)
    )

    private val budgets: List<AppItem>
        get() = _budgets.toList()

    private val eventChange = MutableSharedFlow<Unit>(replay = 1)

    private var resNamIdChecked = NOTHING_VALUE

    override fun itemsFlow(): Flow<List<AppItem>> = flow {
        eventChange.onStart { emit(Unit) }
            .collect {
                _budgets.replaceAll { item ->
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
                emit(budgets)
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