package ru.it_cron.android1.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.dsl.module
import ru.it_cron.android1.navigation.subnavigation.LocalCiceroneHolder

val navigationModule = module {
    single<Cicerone<Router>> {
        Cicerone.create()
    }
    single<Router> {
        get<Cicerone<Router>>().router
    }
    single<NavigatorHolder> {
        get<Cicerone<Router>>().getNavigatorHolder()
    }
    single<LocalCiceroneHolder> {
        LocalCiceroneHolder()
    }
}