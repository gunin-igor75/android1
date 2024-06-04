package ru.it_cron.intern1.domain.repository

import ru.it_cron.intern1.data.model.DataResult

interface ReviewsRepository<T> {

    suspend fun getReviews(): DataResult<T>
}