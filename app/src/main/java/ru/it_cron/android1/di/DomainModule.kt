package ru.it_cron.android1.di

import org.koin.dsl.module
import ru.it_cron.android1.domain.usecases.CheckAvailableUseCase
import ru.it_cron.android1.domain.usecases.GetCasesUseCase
import ru.it_cron.android1.domain.usecases.ReadOnBoardingStateUseCase
import ru.it_cron.android1.domain.usecases.SaveOnBoardingStateUseCase

val domainModule = module {
    factory<SaveOnBoardingStateUseCase> {
        SaveOnBoardingStateUseCase(repository = get())
    }
    factory<ReadOnBoardingStateUseCase> {
        ReadOnBoardingStateUseCase(repository = get())
    }
    factory<CheckAvailableUseCase> {
        CheckAvailableUseCase(repository = get())
    }
    factory<GetCasesUseCase> {
        GetCasesUseCase(repository = get())
    }
}