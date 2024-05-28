package ru.it_cron.android1.choice

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class ChoiceBudgetDefault : ChoiceFilters<String>, ChoiceState<String> {

    private var checkedBudget: String = ""
    private val stateChoice = MutableStateFlow(OnChecked())

    override fun toggle(item: String) {
        if (isChecked(item)) {
            clear(item)
        } else {
            check(item)
        }
    }

    override fun check(item: String) {
        checkedBudget = item
        notifyUpdates()
    }

    override fun clear(item: String) {
        checkedBudget = ""
        notifyUpdates()
    }

    override fun clearAll() {
        checkedBudget = ""
        notifyUpdates()
    }

    override fun stateIn(): Flow<ChoiceState<String>> {
        return stateChoice.map { this }
    }

    override fun isEnabled(): Boolean {
        return checkedBudget != ""
    }

    override fun getEnabledId(): Set<String> {
        return setOf(checkedBudget)
    }

    override fun isChecked(item: String): Boolean {
        return checkedBudget == item
    }

    override fun isEmpty(): Boolean {
        return checkedBudget == ""
    }

    private class OnChecked

    private fun notifyUpdates() {
        stateChoice.value = OnChecked()
    }
}