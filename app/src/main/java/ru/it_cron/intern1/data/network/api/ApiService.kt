package ru.it_cron.intern1.data.network.api

import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import ru.it_cron.intern1.data.network.dto.app_dto.ContainerResponseApp
import ru.it_cron.intern1.data.network.dto.case_dto.ContainerResponseCase
import ru.it_cron.intern1.data.network.dto.cases_dto.ContainerResponseCases
import ru.it_cron.intern1.data.network.dto.company_dto.ContainerResponseReviews
import ru.it_cron.intern1.data.network.dto.filter_dto.ContainerResponseFilters
import ru.it_cron.intern1.data.network.dto.splash_dto.ContainerResponseSplash

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
    suspend fun getFilters(): ContainerResponseFilters

    @GET("testimonials")
    suspend fun getReviews(): ContainerResponseReviews

    @Multipart
    @POST("request")
    suspend fun sendApplication(
        @Part("Services") services: String,
        @Part("Budget") budget: String,
        @Part("Description") description: String,
        @Part files: List<MultipartBody.Part>? = null,
        @Part("ContactName") name: String,
        @Part("ContactCompany") company: String,
        @Part("ContactEmail") email: String,
        @Part("ContactPhone") phone: String,
        @Part("RequestFrom") requestFrom: String,
    ): ContainerResponseApp
}