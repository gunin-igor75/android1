package ru.it_cron.android1.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.it_cron.android1.presentation.cases.CasesAdapter
import ru.it_cron.android1.presentation.cases.CasesViewModel
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
    viewModel<CasesViewModel>{
        CasesViewModel(getCasesUseCase = get())
    }
    factory<CasesAdapter> {
        CasesAdapter(context = get())
    }
}