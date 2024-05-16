package ru.it_cron.android1.choice

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class ChoiceFiltersDefault : ChoiceFilters<String>, ChoiceState<String> {
    private val checkedFilters = HashSet<String>()
    private val stateChoice = MutableStateFlow(OnChecked())

    override fun toggle(item: String) {
        if (isChecked(item)) {
            clear(item)
        } else {
            check(item)
        }
    }

    override fun check(item: String) {
        if (isChecked(item)) {
            return
        } else {
            checkedFilters.add(item)
        }
        notifyUpdates()
    }

    override fun clear(item: String) {
        if (isChecked(item)) {
            checkedFilters.remove(item)
        } else {
            return
        }
        notifyUpdates()
    }

    override fun clearAll() {
        checkedFilters.clear()
        notifyUpdates()
    }

    override fun stateIn(): Flow<ChoiceState<String>> {
        return stateChoice.map { this }
    }

    override fun isChecked(item: String): Boolean {
        return checkedFilters.contains(item)
    }

    private fun notifyUpdates() {
        stateChoice.value = OnChecked()
    }

    private class OnChecked

    override fun isEnabled(): Boolean {
        return checkedFilters.isNotEmpty()
    }

    override fun getEnabledId(): Set<String> {
        return checkedFilters.toSet()
    }

    override fun isEmpty(): Boolean {
        return checkedFilters.isEmpty()
    }
}