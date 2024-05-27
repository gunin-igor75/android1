package ru.it_cron.android1.di

import com.bumptech.glide.Glide
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.it_cron.android1.choice.ChoiceAreaActivityDefault
import ru.it_cron.android1.choice.ChoiceBudgetDefault
import ru.it_cron.android1.choice.ChoiceServicesDefault
import ru.it_cron.android1.presentation.application.ApplicationViewModel
import ru.it_cron.android1.presentation.case_details.CaseDetailsViewModel
import ru.it_cron.android1.presentation.cases.CasesViewModel
import ru.it_cron.android1.presentation.company.CompanyViewModel
import ru.it_cron.android1.presentation.filter.FiltersViewModel
import ru.it_cron.android1.presentation.main.MainViewModel
import ru.it_cron.android1.presentation.onboarding.OnBoardingViewModel

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
            getServicesUseCase = get(),
            getBudgetsUseCase = get(),
            getAreaActivityUseCase = get(),
            deleteFileItemUseCase = get(),
            addFileItemUseCase = get(),
            getFileItemsUseCase = get(),
            isCountFilesUseCase = get(),
            sendAppUseCase = get(),
            clearFileItemsUseCase = get()
        )
    }

    single {
        Glide.with(androidContext())
    }

    scope<ApplicationViewModel> {
        scoped { ChoiceBudgetDefault() }
    }
    scope<ApplicationViewModel> {
        scoped { ChoiceServicesDefault() }
    }
    scope<ApplicationViewModel> {
        scoped { ChoiceAreaActivityDefault() }
    }
}