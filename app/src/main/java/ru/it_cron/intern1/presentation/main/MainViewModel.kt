package ru.it_cron.intern1.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.it_cron.intern1.data.model.DataResult
import ru.it_cron.intern1.domain.model.AvailableState
import ru.it_cron.intern1.domain.usecases.CheckAvailableUseCase
import ru.it_cron.intern1.domain.usecases.ReadOnBoardingStateUseCase

class MainViewModel(
    private val readOnBoardingStateUseCase: ReadOnBoardingStateUseCase,
    private val checkAvailableUseCase: CheckAvailableUseCase,
) : ViewModel() {

    init {
        readBoardingState()
    }

    private var _isCompleted = MutableLiveData<Boolean>()
    val isCompleted: LiveData<Boolean> = _isCompleted

    private var _isAvailable: MutableStateFlow<AvailableState> =
        MutableStateFlow(AvailableState.Initial)
    val isAvailable: StateFlow<AvailableState> = _isAvailable.asStateFlow()

    fun checkAvailable() {
        viewModelScope.launch {
            when (val result = checkAvailableUseCase()) {
                is DataResult.Error -> {
                    _isAvailable.value = AvailableState.Error(result.error)
                }

                is DataResult.Success -> {
                    _isAvailable.value = AvailableState.Success(result.value)
                }

                is DataResult.ErrorInternet -> {}
            }

        }
    }

    private fun readBoardingState() {
        viewModelScope.launch {
            readOnBoardingStateUseCase().collect { completed ->
                _isCompleted.value = completed
            }
        }
    }
}