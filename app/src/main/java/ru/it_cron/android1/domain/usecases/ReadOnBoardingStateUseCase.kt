package ru.it_cron.android1.domain.usecases

import ru.it_cron.android1.domain.repository.OnBoardingRepository

class ReadOnBoardingStateUseCase(
    private val repository: OnBoardingRepository
) {
    operator fun invoke() = repository.readOnBoardingState()
}