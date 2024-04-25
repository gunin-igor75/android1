package ru.it_cron.android1.di

import org.koin.dsl.module
import ru.it_cron.android1.data.repository.OnBoardingRepositoryImpl
import ru.it_cron.android1.domain.repository.OnBoardingRepository


val dataModule = module {
    single<OnBoardingRepository> {
        OnBoardingRepositoryImpl(context = get())
    }
}