package ru.it_cron.android1.data.network.api

import retrofit2.http.GET
import ru.it_cron.android1.data.network.dto.ContainerResponseSplash


interface ApiService {

    @GET("cabinet/menu")
    suspend fun checkAvailableCabinet(): ContainerResponseSplash
}