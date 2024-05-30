package ru.it_cron.android1.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.it_cron.android1.data.network.api.ApiFactory
import ru.it_cron.android1.data.network.api.ApiService
import ru.it_cron.android1.data.repository.AccessRepositoryImpl
import ru.it_cron.android1.data.repository.AppRepositoryImpl
import ru.it_cron.android1.data.repository.CaseDetailsRepositoryImp
import ru.it_cron.android1.data.repository.CasesRepositoryImpl
import ru.it_cron.android1.data.repository.ContactsRepositoryImpl
import ru.it_cron.android1.data.repository.FilterRepositoryImp
import ru.it_cron.android1.data.repository.OnBoardingRepositoryImpl
import ru.it_cron.android1.data.repository.ReviewsRepositoryImpl
import ru.it_cron.android1.domain.model.cases.Case
import ru.it_cron.android1.domain.model.cases.CaseDetails
import ru.it_cron.android1.domain.model.company.Review
import ru.it_cron.android1.domain.model.filter.FiltersGroup
import ru.it_cron.android1.domain.repository.AccessRepository
import ru.it_cron.android1.domain.repository.AppRepository
import ru.it_cron.android1.domain.repository.CaseDetailsRepository
import ru.it_cron.android1.domain.repository.CasesRepository
import ru.it_cron.android1.domain.repository.ContactsRepository
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
    single<AppRepository> {
        AppRepositoryImpl(
            apiService = get(),
            context = androidContext()
        )
    }

    factory {
        CoroutineScope(Dispatchers.IO + SupervisorJob())
    }

    single<ContactsRepository> {
        ContactsRepositoryImpl(scope = get())
    }
}