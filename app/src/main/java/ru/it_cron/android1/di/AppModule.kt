package ru.it_cron.android1.di

import com.bumptech.glide.Glide
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.it_cron.android1.di.ChoiceType.CHOICE_AREA_DEFAULT
import ru.it_cron.android1.di.ChoiceType.CHOICE_BUDGET_DEFAULT
import ru.it_cron.android1.di.ChoiceType.CHOICE_FILTER_DEFAULT
import ru.it_cron.android1.di.ChoiceType.CHOICE_SERVICE_DEFAULT
import ru.it_cron.android1.presentation.application.ApplicationFragment
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
            choiceFilters = get(named(CHOICE_FILTER_DEFAULT))
        )
    }

    viewModel<CaseDetailsViewModel> {
        CaseDetailsViewModel(getCaseDetailsUseCase = get())
    }

    viewModel<FiltersViewModel> {
        FiltersViewModel(
            getFiltersUseCase = get(),
            choiceFilters = get(named(CHOICE_FILTER_DEFAULT))
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
            clearFileItemsUseCase = get(),
            choiceServices = get(named(CHOICE_SERVICE_DEFAULT)),
            choiceBudget = get(named(CHOICE_BUDGET_DEFAULT)),
            choiceAreaActivity = get(named(CHOICE_AREA_DEFAULT))
        )
    }

    single {
        Glide.with(androidContext())
    }

    scope<ApplicationFragment> {
        viewModel {
            ApplicationViewModel(
                getServicesUseCase = get(),
                getBudgetsUseCase = get(),
                getAreaActivityUseCase = get(),
                deleteFileItemUseCase = get(),
                addFileItemUseCase = get(),
                getFileItemsUseCase = get(),
                isCountFilesUseCase = get(),
                sendAppUseCase = get(),
                clearFileItemsUseCase = get(),
                choiceServices = get(named(CHOICE_SERVICE_DEFAULT)),
                choiceBudget = get(named(CHOICE_BUDGET_DEFAULT)),
                choiceAreaActivity = get(named(CHOICE_AREA_DEFAULT))
            )
        }
    }
}