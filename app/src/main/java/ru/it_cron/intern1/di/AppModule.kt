package ru.it_cron.intern1.di

import com.bumptech.glide.Glide
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.it_cron.intern1.presentation.application.ApplicationFragment
import ru.it_cron.intern1.presentation.application.ApplicationViewModel
import ru.it_cron.intern1.presentation.case_details.CaseDetailsViewModel
import ru.it_cron.intern1.presentation.cases.CasesViewModel
import ru.it_cron.intern1.presentation.company.CompanyViewModel
import ru.it_cron.intern1.presentation.contacts.ContactsFragment
import ru.it_cron.intern1.presentation.contacts.ContactsViewModel
import ru.it_cron.intern1.presentation.filter.FiltersViewModel
import ru.it_cron.intern1.presentation.main.MainViewModel
import ru.it_cron.intern1.presentation.onboarding.OnBoardingViewModel
import ru.it_cron.intern1.presentation.reviews.ReviewsFragment
import ru.it_cron.intern1.presentation.reviews.ReviewsViewModel

val appModule = module {
    viewModel<OnBoardingViewModel> {
        OnBoardingViewModel(saveOnBoardingStateUseCase = get())
    }
    viewModel<MainViewModel> {
        MainViewModel(
            readOnBoardingStateUseCase = get(),
            checkAvailableUseCase = get()
        )
    }
    viewModel<CasesViewModel> {
        CasesViewModel(
            getCasesUseCase = get(),
            choiceFilters = get()
        )
    }

    viewModel<CaseDetailsViewModel> {
        CaseDetailsViewModel(getCaseDetailsUseCase = get())
    }

    viewModel<FiltersViewModel> {
        FiltersViewModel(
            getFiltersUseCase = get(),
            choiceFilters = get()
        )
    }

    viewModel<CompanyViewModel> {
        CompanyViewModel(getReviewsUseCase = get())
    }

    viewModel<ApplicationViewModel> {
        ApplicationViewModel(
            serviceInterActor = get(),
            budgetInterActor = get(),
            areaActivityInterActor = get(),
            fileItemsInterActor = get(),
            sendAppUseCase = get()
        )
    }

    single {
        Glide.with(androidContext())
    }

    scope<ApplicationFragment> {
        viewModel {
            ApplicationViewModel(
                serviceInterActor = get(),
                budgetInterActor = get(),
                areaActivityInterActor = get(),
                fileItemsInterActor = get(),
                sendAppUseCase = get()
            )
        }
    }
    viewModel<ContactsViewModel> {
        ContactsViewModel(getDaySItemsUseCase = get())
    }
    scope<ContactsFragment> {
        viewModel {
            ContactsViewModel(getDaySItemsUseCase = get())
        }
    }

    viewModel<ReviewsViewModel>(){ parameters ->
        ReviewsViewModel(initialReviews = parameters.get())
    }
    scope<ReviewsFragment> {
        viewModel { parameters ->
            ReviewsViewModel(initialReviews = parameters.get())
        }
    }
}