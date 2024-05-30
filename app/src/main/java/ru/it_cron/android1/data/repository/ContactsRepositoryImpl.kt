package ru.it_cron.android1.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.it_cron.android1.R
import ru.it_cron.android1.domain.model.contacts.DayWeekItem
import ru.it_cron.android1.domain.model.contacts.TypeDay
import ru.it_cron.android1.domain.model.contacts.TypeDay.DAY_OF
import ru.it_cron.android1.domain.model.contacts.TypeDay.FRIDAY
import ru.it_cron.android1.domain.model.contacts.TypeDay.MONDAY
import ru.it_cron.android1.domain.model.contacts.TypeDay.THURSDAY
import ru.it_cron.android1.domain.model.contacts.TypeDay.TUESDAY
import ru.it_cron.android1.domain.model.contacts.TypeDay.WEDNESDAY
import ru.it_cron.android1.domain.repository.ContactsRepository
import java.util.Calendar

class ContactsRepositoryImpl(
    private val scope: CoroutineScope,
) : ContactsRepository {

    private val _daysItems = mutableListOf(
        DayWeekItem(MONDAY, R.string.schedule_title_mon, false),
        DayWeekItem(TUESDAY, R.string.schedule_title_tues, false),
        DayWeekItem(WEDNESDAY, R.string.schedule_title_wed, false),
        DayWeekItem(THURSDAY, R.string.schedule_title_thur, false),
        DayWeekItem(FRIDAY, R.string.schedule_title_fr, false),
        DayWeekItem(DAY_OF, R.string.schedule_title_day_of, false),
    )
    private val daysItems: List<DayWeekItem>
        get() = _daysItems.toList()

    private val _eventChange: MutableSharedFlow<Unit> = MutableSharedFlow()

    init {
        triggerToggle()
    }

    override fun getDaysItems(): Flow<List<DayWeekItem>> = flow {
        _eventChange.onStart { emit(Unit) }
            .collect {
                val calendar = Calendar.getInstance()
                val index = calendar.get(Calendar.DAY_OF_WEEK)
                val currentDayType = getTypeDay(index)
                _daysItems.replaceAll { item ->
                    if (item.day == currentDayType) {
                        item.copy(isCurrent = true)
                    } else {
                        item
                    }
                }
                emit(daysItems)
            }
    }

    private fun triggerToggle() {
        scope.launch {
            while (true) {
                delay(PERIOD)
                _eventChange.emit(Unit)
            }
        }
    }

    private fun getTypeDay(index: Int): TypeDay {
        return when (index) {
            2 -> {
                MONDAY
            }

            3 -> {
                TUESDAY
            }

            4 -> {
                WEDNESDAY
            }

            5 -> {
                THURSDAY
            }

            6 -> {
                FRIDAY
            }

            else -> {
                DAY_OF
            }
        }
    }

    private companion object {
        private const val PERIOD = 216_000_000L
    }
}

fun main() {
    val cal = Calendar.getInstance()
    val res = cal.get(Calendar.DAY_OF_WEEK)
    println(res)
}