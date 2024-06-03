package ru.it_cron.intern1.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.it_cron.intern1.domain.model.cases.CaseBox
import ru.it_cron.intern1.domain.model.filter.ContainerImage
import ru.it_cron.intern1.presentation.application.ApplicationFragment
import ru.it_cron.intern1.presentation.application.ErrorAppFragment
import ru.it_cron.intern1.presentation.application.PersonalInfoFragment
import ru.it_cron.intern1.presentation.application.PoliticFragment
import ru.it_cron.intern1.presentation.application.SendAppOkFragment
import ru.it_cron.intern1.presentation.case_details.CaseFragment
import ru.it_cron.intern1.presentation.case_details.ImagesFragment
import ru.it_cron.intern1.presentation.cases.CasesFragment
import ru.it_cron.intern1.presentation.company.CompanyFragment
import ru.it_cron.intern1.presentation.contacts.ContactsFragment
import ru.it_cron.intern1.presentation.error.ErrorFragment
import ru.it_cron.intern1.presentation.filter.FilterFragment
import ru.it_cron.intern1.presentation.home.HomeFragment
import ru.it_cron.intern1.presentation.login.LoginFragment
import ru.it_cron.intern1.presentation.onboarding.AppIntro
import ru.it_cron.intern1.presentation.services.ServicesFragment

object Screens {
    fun openOnBoardingFragment() = FragmentScreen {
        AppIntro.newInstance()
    }

    fun openHomeFragment() = FragmentScreen {
        HomeFragment.newInstance()
    }

    fun openErrorFragment() = FragmentScreen {
        ErrorFragment.newInstance()
    }

    fun openCasesFragment() = FragmentScreen {
        CasesFragment.newInstance()
    }

    fun openCompanyFragment() = FragmentScreen {
        CompanyFragment.newInstance()
    }

    fun openServicesFragment() = FragmentScreen {
        ServicesFragment.newInstance()
    }

    fun openContactsFragment() = FragmentScreen {
        ContactsFragment.newInstance()
    }

    fun openLoginFragment() = FragmentScreen {
        LoginFragment.newInstance()
    }

    fun openCaseFragment(caseBox: CaseBox) = FragmentScreen {
        CaseFragment.newInstance(caseBox)
    }

    fun openImagesFragment(containerData: ContainerImage) = FragmentScreen {
        ImagesFragment.newInstance(containerData)
    }

    fun openFiltersFragment() = FragmentScreen {
        FilterFragment.newInstance()
    }

    fun openApplicationFragment() = FragmentScreen {
        ApplicationFragment.newInstance()
    }

    fun openPersonalInfoFragment() = FragmentScreen {
        PersonalInfoFragment.newInstance()
    }

    fun openPoliticFragment() = FragmentScreen {
        PoliticFragment.newInstance()
    }
    fun openErrorAppFragment() = FragmentScreen{
        ErrorAppFragment.newInstance()
    }
    fun openSendAppOkFragment() = FragmentScreen{
        SendAppOkFragment.newInstance()
    }
}