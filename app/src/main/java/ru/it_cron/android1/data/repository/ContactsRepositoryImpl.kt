package ru.it_cron.android1.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.it_cron.android1.R
import ru.it_cron.android1.domain.model.contacts.DayItem
import ru.it_cron.android1.domain.model.contacts.TypeDay
import ru.it_cron.android1.domain.model.contacts.TypeDay.DAY_OF
import ru.it_cron.android1.domain.model.contacts.TypeDay.FRIDAY
import ru.it_cron.android1.domain.model.contacts.TypeDay.MONDAY
import ru.it_cron.android1.domain.model.contacts.TypeDay.THURSDAY
import ru.it_cron.android1.domain.model.contacts.TypeDay.TUESDAY
import ru.it_cron.android1.domain.model.contacts.TypeDay.WEDNESDAY
import ru.it_cron.android1.domain.repository.ContactsRepository
import java.util.Calendar
import java.util.TimeZone

class ContactsRepositoryImpl(
    private val scope: CoroutineScope,
) : ContactsRepository {

    private val _daysItems = mutableListOf(
        DayItem(MONDAY, R.string.schedule_title_mon, false),
        DayItem(TUESDAY, R.string.schedule_title_tues, false),
        DayItem(WEDNESDAY, R.string.schedule_title_wed, false),
        DayItem(THURSDAY, R.string.schedule_title_thur, false),
        DayItem(FRIDAY, R.string.schedule_title_fr, false),
        DayItem(DAY_OF, R.string.schedule_title_day_of, false),
    )
    private val daysItems: List<DayItem>
        get() = _daysItems.toList()

    private val _eventChange: MutableSharedFlow<Unit> = MutableSharedFlow(replay = 1)

    init {
        triggerToggle()
    }

    override fun getDaysItems(): Flow<List<DayItem>> = flow {
        _eventChange.onStart { emit(Unit) }.collect {
            val index = getCurrentIndexDay()
            val currentDayType = getTypeDay(index)
            _daysItems.replaceAll { item ->
                if (item.typeDay == currentDayType) {
                    item.copy(isCurrent = true)
                } else {
                    item
                }
            }
            Log.d(TAG, index.toString())
            emit(daysItems)
        }
    }

    private fun getCurrentIndexDay(): Int {
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone(TIME_ZONE)
        val index = calendar.get(Calendar.DAY_OF_WEEK)
        return index
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
        private const val TEST_PERIOD = 3000L
        private const val TAG = "ContactsRepositoryImpl"
        private const val TIME_ZONE = "Europe/Moscow"
    }
}

