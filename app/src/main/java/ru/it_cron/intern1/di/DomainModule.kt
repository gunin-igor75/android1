package ru.it_cron.intern1.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.it_cron.intern1.choice.ChoiceFilters
import ru.it_cron.intern1.choice.ChoiceFiltersDefault
import ru.it_cron.intern1.di.AppItemType.AREA_ACTIVITY
import ru.it_cron.intern1.di.AppItemType.BUDGET
import ru.it_cron.intern1.di.AppItemType.SERVICE
import ru.it_cron.intern1.domain.interactors.application.AreaActivityInteractor
import ru.it_cron.intern1.domain.interactors.application.BudgetInteractor
import ru.it_cron.intern1.domain.interactors.application.FileItemsInteractor
import ru.it_cron.intern1.domain.interactors.application.ServiceInteractor
import ru.it_cron.intern1.domain.usecases.CheckAvailableUseCase
import ru.it_cron.intern1.domain.usecases.GetCaseDetailsUseCase
import ru.it_cron.intern1.domain.usecases.GetCasesStorageUseCase
import ru.it_cron.intern1.domain.usecases.GetCasesUseCase
import ru.it_cron.intern1.domain.usecases.GetFiltersUseCase
import ru.it_cron.intern1.domain.usecases.GetReviewsUseCase
import ru.it_cron.intern1.domain.usecases.ReadOnBoardingStateUseCase
import ru.it_cron.intern1.domain.usecases.SaveOnBoardingStateUseCase
import ru.it_cron.intern1.domain.usecases.application.SendAppUseCase
import ru.it_cron.intern1.domain.usecases.application.areaActivity.ClearAllAreaActivityUseCase
import ru.it_cron.intern1.domain.usecases.application.areaActivity.GetAreaActivityItemUseCase
import ru.it_cron.intern1.domain.usecases.application.areaActivity.GetSelectedAreaActivityUseCase
import ru.it_cron.intern1.domain.usecases.application.areaActivity.IsNotEmptySelectedAreaActivityUseCase
import ru.it_cron.intern1.domain.usecases.application.areaActivity.ToggleAreaActivityUseCase
import ru.it_cron.intern1.domain.usecases.application.budgets.ClearAllBudgetUseCase
import ru.it_cron.intern1.domain.usecases.application.budgets.GetBudgetsItemUseCase
import ru.it_cron.intern1.domain.usecases.application.budgets.GetSelectedBudgetsUseCase
import ru.it_cron.intern1.domain.usecases.application.budgets.IsNotEmptySelectedBudgetUseCase
import ru.it_cron.intern1.domain.usecases.application.budgets.ToggleBudgetsUseCase
import ru.it_cron.intern1.domain.usecases.application.files.AddFileItemUseCase
import ru.it_cron.intern1.domain.usecases.application.files.ClearFileItemsUseCase
import ru.it_cron.intern1.domain.usecases.application.files.DeleteFileItemUseCase
import ru.it_cron.intern1.domain.usecases.application.files.GetFileItemsUseCase
import ru.it_cron.intern1.domain.usecases.application.files.IsCountFilesUseCase
import ru.it_cron.intern1.domain.usecases.application.services.ClearAllServiceUseCase
import ru.it_cron.intern1.domain.usecases.application.services.GetSelectedServicesUseCase
import ru.it_cron.intern1.domain.usecases.application.services.GetServicesItemUseCase
import ru.it_cron.intern1.domain.usecases.application.services.IsNotEmptySelectedServiceUseCase
import ru.it_cron.intern1.domain.usecases.application.services.ToggleServiceUseCase
import ru.it_cron.intern1.domain.usecases.contacts.GetDaySItemsUseCase


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

    single<ChoiceFilters<String>> {
        ChoiceFiltersDefault()
    }


    factory<SendAppUseCase> {
        SendAppUseCase(repository = get())
    }

    factory<GetDaySItemsUseCase> {
        GetDaySItemsUseCase(repository = get())
    }

    /*Service use case interActor*/

    factory<GetServicesItemUseCase> {
        GetServicesItemUseCase(repository = get(named(SERVICE)))
    }

    factory<GetSelectedServicesUseCase> {
        GetSelectedServicesUseCase(repository = get(named(SERVICE)))
    }

    factory<ToggleServiceUseCase> {
        ToggleServiceUseCase(repository = get(named(SERVICE)))
    }

    factory<ClearAllServiceUseCase> {
        ClearAllServiceUseCase(repository = get(named(SERVICE)))
    }
    factory<IsNotEmptySelectedServiceUseCase> {
        IsNotEmptySelectedServiceUseCase(repository = get(named(SERVICE)))
    }

    factory<ServiceInteractor> {
        ServiceInteractor(
            items = get(),
            selectedItems = get(),
            toggle = get(),
            clearAll = get(),
            isNotEmpty = get()
        )
    }

    /*Budget use case interActor*/

    factory<GetBudgetsItemUseCase> {
        GetBudgetsItemUseCase(repository = get(named(BUDGET)))
    }

    factory<GetSelectedBudgetsUseCase> {
        GetSelectedBudgetsUseCase(repository = get(named(BUDGET)))
    }

    factory<ToggleBudgetsUseCase> {
        ToggleBudgetsUseCase(repository = get(named(BUDGET)))
    }

    factory<ClearAllBudgetUseCase> {
        ClearAllBudgetUseCase(repository = get(named(BUDGET)))
    }

    factory<IsNotEmptySelectedBudgetUseCase> {
        IsNotEmptySelectedBudgetUseCase(repository = get(named(BUDGET)))
    }

    factory<BudgetInteractor> {
        BudgetInteractor(
            items = get(),
            selectedItems = get(),
            toggle = get(),
            clearAll = get(),
            isNotEmpty = get()
        )
    }

    /*Budget use case interActor*/

    factory<GetAreaActivityItemUseCase> {
        GetAreaActivityItemUseCase(repository = get(named(AREA_ACTIVITY)))
    }

    factory<GetSelectedAreaActivityUseCase> {
        GetSelectedAreaActivityUseCase(repository = get(named(AREA_ACTIVITY)))
    }

    factory<ToggleAreaActivityUseCase> {
        ToggleAreaActivityUseCase(repository = get(named(AREA_ACTIVITY)))
    }

    factory<ClearAllAreaActivityUseCase> {
        ClearAllAreaActivityUseCase(repository = get(named(AREA_ACTIVITY)))
    }

    factory<IsNotEmptySelectedAreaActivityUseCase> {
        IsNotEmptySelectedAreaActivityUseCase(repository = get(named(AREA_ACTIVITY)))
    }

    factory<AreaActivityInteractor> {
        AreaActivityInteractor(
            items = get(),
            selectedItems = get(),
            toggle = get(),
            clearAll = get(),
            isNotEmpty = get()
        )
    }

    /*File items use case interActor*/

    factory<ClearFileItemsUseCase> {
        ClearFileItemsUseCase(repository = get())
    }
    factory<DeleteFileItemUseCase> {
        DeleteFileItemUseCase(repository = get())
    }
    factory<AddFileItemUseCase> {
        AddFileItemUseCase(repository = get())
    }
    factory<GetFileItemsUseCase> {
        GetFileItemsUseCase(repository = get())
    }
    factory<IsCountFilesUseCase> {
        IsCountFilesUseCase(repository = get())
    }
    factory<FileItemsInteractor> {
        FileItemsInteractor(
            fileItems = get(),
            addFileItem = get(),
            deleteFileItem = get(),
            isCountFiles = get(),
            clearFileItem = get(),
        )
    }
    factory<GetCasesStorageUseCase> {
        GetCasesStorageUseCase(repository = get())
    }
}