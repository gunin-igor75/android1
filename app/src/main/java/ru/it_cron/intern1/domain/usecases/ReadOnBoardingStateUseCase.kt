package ru.it_cron.intern1.domain.usecases

import ru.it_cron.intern1.domain.repository.OnBoardingRepository

class ReadOnBoardingStateUseCase(
    private val repository: OnBoardingRepository,
) {
    operator fun invoke() = repository.readOnBoardingState()
}