package ru.it_cron.android1.domain.repository

import ru.it_cron.android1.data.model.DataResult

interface ReviewsRepository<T> {

    suspend fun getReviews(): DataResult<T>
}