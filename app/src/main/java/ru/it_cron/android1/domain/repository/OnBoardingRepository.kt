package ru.it_cron.android1.domain.repository

import kotlinx.coroutines.flow.Flow

interface OnBoardingRepository {

    suspend fun saveOnBoardingState(completed: Boolean)

    fun readOnBoardingState(): Flow<Boolean>
}