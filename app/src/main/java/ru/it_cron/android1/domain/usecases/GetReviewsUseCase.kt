package ru.it_cron.android1.domain.usecases

import ru.it_cron.android1.domain.model.company.Review
import ru.it_cron.android1.domain.repository.ReviewsRepository

class GetReviewsUseCase(
    private val repository: ReviewsRepository<List<Review>>,
) {
    suspend operator fun invoke() = repository.getReviews()
}