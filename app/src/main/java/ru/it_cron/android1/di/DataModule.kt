package ru.it_cron.android1.di

import org.koin.dsl.module
import ru.it_cron.android1.data.network.api.ApiFactory
import ru.it_cron.android1.data.network.api.ApiService
import ru.it_cron.android1.data.repository.AccessRepositoryImpl
import ru.it_cron.android1.data.repository.CasesRepositoryImpl
import ru.it_cron.android1.data.repository.OnBoardingRepositoryImpl
import ru.it_cron.android1.domain.model.Case
import ru.it_cron.android1.domain.repository.AccessRepository
import ru.it_cron.android1.domain.repository.CasesRepository
import ru.it_cron.android1.domain.repository.OnBoardingRepository


val dataModule = module {
    single<OnBoardingRepository> {
        OnBoardingRepositoryImpl(context = get())
    }
    single<ApiService> {
        ApiFactory.apiService
    }
    single<AccessRepository<Boolean>> {
        AccessRepositoryImpl(apiService = get())
    }
    single<CasesRepository<List<Case>>> {
        CasesRepositoryImpl(apiService = get())
    }
}