package ru.it_cron.intern1.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import ru.it_cron.intern1.domain.usecases.SaveOnBoardingStateUseCase

class OnBoardingViewModel(
    private val saveOnBoardingStateUseCase: SaveOnBoardingStateUseCase,
) : ViewModel() {

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch {
            saveOnBoardingStateUseCase(completed)
        }
    }
}