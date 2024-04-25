package ru.it_cron.android1.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.it_cron.android1.presentation.home.HomeFragment
import ru.it_cron.android1.presentation.onboarding.AppIntro

object Screens {
    fun openOnBoardingFragment() = FragmentScreen {
        AppIntro.newInstance()
    }
    fun openHomeFragment() = FragmentScreen{
        HomeFragment.newInstance()
    }
}