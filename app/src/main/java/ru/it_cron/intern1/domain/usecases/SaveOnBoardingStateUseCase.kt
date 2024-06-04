package ru.it_cron.intern1.domain.usecases

import ru.it_cron.intern1.domain.repository.OnBoardingRepository

class SaveOnBoardingStateUseCase(
    private val repository: OnBoardingRepository,
) {
    suspend operator fun invoke(completed: Boolean) = repository.saveOnBoardingState(completed)
}