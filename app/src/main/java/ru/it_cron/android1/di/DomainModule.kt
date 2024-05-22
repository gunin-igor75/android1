package ru.it_cron.android1.di

import org.koin.dsl.module
import ru.it_cron.android1.choice.ChoiceFilters
import ru.it_cron.android1.choice.ChoiceFiltersDefault
import ru.it_cron.android1.domain.usecases.CheckAvailableUseCase
import ru.it_cron.android1.domain.usecases.GetCaseDetailsUseCase
import ru.it_cron.android1.domain.usecases.GetCasesUseCase
import ru.it_cron.android1.domain.usecases.GetFiltersUseCase
import ru.it_cron.android1.domain.usecases.GetReviewsUseCase
import ru.it_cron.android1.domain.usecases.ReadOnBoardingStateUseCase
import ru.it_cron.android1.domain.usecases.SaveOnBoardingStateUseCase
import ru.it_cron.android1.domain.usecases.application.GetAreaActivityUseCase
import ru.it_cron.android1.domain.usecases.application.GetBudgetsUseCase
import ru.it_cron.android1.domain.usecases.application.GetServicesUseCase

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
    factory<GetCaseDetailsUseCase> {
        GetCaseDetailsUseCase(repository = get())
    }
    factory<GetFiltersUseCase> {
        GetFiltersUseCase(repository = get())
    }
    factory<GetReviewsUseCase> {
        GetReviewsUseCase(repository = get())
    }
    factory<ChoiceFilters<String>> {
        ChoiceFiltersDefault()
    }

    factory<GetServicesUseCase> {
        GetServicesUseCase(repository = get())
    }
    factory<GetBudgetsUseCase> {
        GetBudgetsUseCase(repository = get())
    }
    factory<GetAreaActivityUseCase> {
        GetAreaActivityUseCase(repository = get())
    }
}