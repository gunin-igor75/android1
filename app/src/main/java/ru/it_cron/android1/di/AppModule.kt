package ru.it_cron.android1.di

import com.bumptech.glide.Glide
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.it_cron.android1.presentation.case_details.CaseDetailsViewModel
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

    viewModel<CaseDetailsViewModel>{
        CaseDetailsViewModel(getCaseDetailsUseCase = get())
    }

    single {
        Glide.with(androidContext())
    }
}