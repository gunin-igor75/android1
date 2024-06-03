package ru.it_cron.intern1.data.repository

import ru.it_cron.intern1.R
import ru.it_cron.intern1.domain.model.contacts.DayItem
import ru.it_cron.intern1.domain.model.contacts.TypeDay
import ru.it_cron.intern1.domain.model.contacts.TypeDay.DAY_OF
import ru.it_cron.intern1.domain.model.contacts.TypeDay.FRIDAY
import ru.it_cron.intern1.domain.model.contacts.TypeDay.MONDAY
import ru.it_cron.intern1.domain.model.contacts.TypeDay.THURSDAY
import ru.it_cron.intern1.domain.model.contacts.TypeDay.TUESDAY
import ru.it_cron.intern1.domain.model.contacts.TypeDay.WEDNESDAY
import ru.it_cron.intern1.domain.repository.ContactsRepository
import java.util.Calendar
import java.util.TimeZone

class ContactsRepositoryImpl : ContactsRepository {

    private val _daysItems = mutableListOf(
        DayItem(MONDAY, R.string.schedule_title_mon, false),
        DayItem(TUESDAY, R.string.schedule_title_tues, false),
        DayItem(WEDNESDAY, R.string.schedule_title_wed, false),
        DayItem(THURSDAY, R.string.schedule_title_thur, false),
        DayItem(FRIDAY, R.string.schedule_title_fr, false),
        DayItem(DAY_OF, R.string.schedule_title_day_of, false),
    )

    private val currentType: TypeDay by lazy {
        val currentIndex = getCurrentIndexDay()
        getTypeDay(currentIndex)
    }

    override fun getDaysItems(): List<DayItem> {
        _daysItems.replaceAll { item ->
            if (item.typeDay == currentType) {
                item.copy(isCurrent = true)
            } else {
                item
            }
        }
        return _daysItems.toList()
    }


    private fun getCurrentIndexDay(): Int {
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone(TIME_ZONE)
        val index = calendar.get(Calendar.DAY_OF_WEEK)
        return index
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
        private const val TIME_ZONE = "Europe/Moscow"
    }
}

