package ru.it_cron.android1.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.it_cron.android1.CaseFragment
import ru.it_cron.android1.presentation.cases.CasesFragment
import ru.it_cron.android1.presentation.company.CompanyFragment
import ru.it_cron.android1.presentation.contacts.ContactsFragment
import ru.it_cron.android1.presentation.error.ErrorFragment
import ru.it_cron.android1.presentation.home.HomeFragment
import ru.it_cron.android1.presentation.login.LoginFragment
import ru.it_cron.android1.presentation.onboarding.AppIntro
import ru.it_cron.android1.presentation.services.ServicesFragment

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
    fun openServicesFragment() = FragmentScreen{
        ServicesFragment.newInstance()
    }
    fun openContactsFragment() = FragmentScreen{
        ContactsFragment.newInstance()
    }
    fun openLoginFragment() = FragmentScreen{
        LoginFragment.newInstance()
    }
    fun openCaseFragment(caseId: String) = FragmentScreen {
        CaseFragment.newInstance(caseId)
    }
}