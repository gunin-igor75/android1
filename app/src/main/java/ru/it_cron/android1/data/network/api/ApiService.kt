package ru.it_cron.android1.data.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.it_cron.android1.data.network.dto.case_dto.ContainerResponseCase
import ru.it_cron.android1.data.network.dto.cases_dto.ContainerResponseCases
import ru.it_cron.android1.data.network.dto.company_dto.ContainerResponseReviews
import ru.it_cron.android1.data.network.dto.filter_dto.ContainerResponseFilters
import ru.it_cron.android1.data.network.dto.splash_dto.ContainerResponseSplash

interface ApiService {

    @GET("cabinet/menu")
    suspend fun checkAvailableCabinet(): ContainerResponseSplash

    @GET("cases")
    suspend fun getCases(): ContainerResponseCases

    @GET("cases/{id}")
    suspend fun getCase(
        @Path("id") id: String,
    ): ContainerResponseCase

    @GET("filters")
    suspend fun getFilters(
    ): ContainerResponseFilters

    @GET("testimonials")
    suspend fun getReviews(): ContainerResponseReviews
}