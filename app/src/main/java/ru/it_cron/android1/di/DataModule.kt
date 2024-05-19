package ru.it_cron.android1.di

import org.koin.dsl.module
import ru.it_cron.android1.data.network.api.ApiFactory
import ru.it_cron.android1.data.network.api.ApiService
import ru.it_cron.android1.data.repository.AccessRepositoryImpl
import ru.it_cron.android1.data.repository.CaseDetailsRepositoryImp
import ru.it_cron.android1.data.repository.CasesRepositoryImpl
import ru.it_cron.android1.data.repository.FilterRepositoryImp
import ru.it_cron.android1.data.repository.OnBoardingRepositoryImpl
import ru.it_cron.android1.data.repository.ReviewsRepositoryImpl
import ru.it_cron.android1.domain.model.Case
import ru.it_cron.android1.domain.model.CaseDetails
import ru.it_cron.android1.domain.model.FiltersGroup
import ru.it_cron.android1.domain.model.Review
import ru.it_cron.android1.domain.repository.AccessRepository
import ru.it_cron.android1.domain.repository.CaseDetailsRepository
import ru.it_cron.android1.domain.repository.CasesRepository
import ru.it_cron.android1.domain.repository.FilterRepository
import ru.it_cron.android1.domain.repository.OnBoardingRepository
import ru.it_cron.android1.domain.repository.ReviewsRepository


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
    single<CaseDetailsRepository<CaseDetails>> {
        CaseDetailsRepositoryImp(apiService = get())
    }
    single<FilterRepository<List<FiltersGroup>>> {
        FilterRepositoryImp(apiService = get())
    }
    single<ReviewsRepository<List<Review>>> {
        ReviewsRepositoryImpl(apiService = get())
    }
}