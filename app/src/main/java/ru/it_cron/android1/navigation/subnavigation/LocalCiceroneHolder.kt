package ru.it_cron.android1.navigation.subnavigation

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

class LocalCiceroneHolder {
    private val containers = HashMap<String, Cicerone<Router>>()

    fun getCicerone(containerTg: String): Cicerone<Router> =
        containers.getOrPut(containerTg) {
            Cicerone.create()
        }
}