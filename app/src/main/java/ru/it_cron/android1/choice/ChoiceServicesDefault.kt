package ru.it_cron.android1.choice

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class ChoiceServicesDefault : ChoiceFilters<String>, ChoiceState<String> {
    private val checkedServices = HashSet<String>()
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
            checkedServices.add(item)
        }
        notifyUpdates()
    }

    override fun clear(item: String) {
        if (isChecked(item)) {
            checkedServices.remove(item)
        } else {
            return
        }
        notifyUpdates()
    }

    override fun clearAll() {
        checkedServices.clear()
        notifyUpdates()
    }

    override fun stateIn(): Flow<ChoiceState<String>> {
        return stateChoice.map { this }
    }

    override fun isChecked(item: String): Boolean {
        return checkedServices.contains(item)
    }

    private fun notifyUpdates() {
        stateChoice.value = OnChecked()
    }

    private class OnChecked

    override fun isEnabled(): Boolean {
        return checkedServices.isNotEmpty()
    }

    override fun getEnabledId(): Set<String> {
        return checkedServices.toSet()
    }

    override fun isEmpty(): Boolean {
        return checkedServices.isEmpty()
    }
}