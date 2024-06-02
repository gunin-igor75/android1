package ru.it_cron.android1.presentation.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.it_cron.android1.domain.model.contacts.DayItem
import ru.it_cron.android1.domain.usecases.contacts.GetDaySItemsUseCase

class ContactsViewModel(
    getDaySItemsUseCase: GetDaySItemsUseCase,
) : ViewModel() {

    private val _daysItems: MutableLiveData<List<DayItem>> = MutableLiveData(getDaySItemsUseCase())

    val daysItems: LiveData<List<DayItem>> = _daysItems
}
