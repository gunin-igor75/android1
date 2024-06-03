package ru.it_cron.intern1.domain.repository

import kotlinx.coroutines.flow.Flow

interface OnBoardingRepository {

    suspend fun saveOnBoardingState(completed: Boolean)

    fun readOnBoardingState(): Flow<Boolean>
}