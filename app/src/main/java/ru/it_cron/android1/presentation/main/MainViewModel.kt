package ru.it_cron.android1.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.it_cron.android1.domain.usecases.ReadOnBoardingStateUseCase

class MainViewModel(
    private val readOnBoardingStateUseCase: ReadOnBoardingStateUseCase,
) : ViewModel() {

    init {
        readBoardingState()
    }

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isCompleted = MutableLiveData<Boolean>()
    val isCompleted: LiveData<Boolean> = _isCompleted

    private fun readBoardingState() {
        viewModelScope.launch {
            readOnBoardingStateUseCase().collect { completed ->
                _isCompleted.value = completed
                _isLoading.value = false
            }
        }
    }
}