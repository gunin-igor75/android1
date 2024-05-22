package ru.it_cron.android1.choice

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class ChoiceAreaActivityDefault : ChoiceFilters<String>, ChoiceState<String> {

    private var checkedAreaActivity: String = ""
    private val stateChoice = MutableStateFlow(OnChecked())

    override fun toggle(item: String) {
        if (isChecked(item)) {
            clear(item)
        } else {
            check(item)
        }
    }

    override fun check(item: String) {
        checkedAreaActivity = item
        notifyUpdates()
    }

    override fun clear(item: String) {
        checkedAreaActivity = ""
        notifyUpdates()
    }

    override fun clearAll() {
        checkedAreaActivity = ""
        notifyUpdates()
    }

    override fun stateIn(): Flow<ChoiceState<String>> {
        return stateChoice.map { this }
    }

    override fun isEnabled(): Boolean {
        return checkedAreaActivity != ""
    }

    override fun getEnabledId(): Set<String> {
        return setOf(checkedAreaActivity)
    }

    override fun isChecked(item: String): Boolean {
        return checkedAreaActivity == item
    }

    override fun isEmpty(): Boolean {
        return checkedAreaActivity == ""
    }

    private class OnChecked

    private fun notifyUpdates() {
        stateChoice.value = OnChecked()
    }
}