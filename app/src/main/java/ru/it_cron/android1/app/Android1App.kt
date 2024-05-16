package ru.it_cron.android1.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.it_cron.android1.di.appModule
import ru.it_cron.android1.di.dataModule
import ru.it_cron.android1.di.domainModule
import ru.it_cron.android1.di.navigationModule


class Android1App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@Android1App)
            modules(
                listOf(
                    dataModule,
                    domainModule,
                    appModule,
                    navigationModule
                )
            )
        }
    }
}