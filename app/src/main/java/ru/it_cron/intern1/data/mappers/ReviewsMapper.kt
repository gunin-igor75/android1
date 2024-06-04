package ru.it_cron.intern1.data.mappers

import ru.it_cron.intern1.data.network.dto.company_dto.ReviewDto
import ru.it_cron.intern1.domain.model.company.Review


fun ReviewDto.reviewsDtoToReview() = Review(
    id, customerName, company, text, urlIcon
)

fun List<ReviewDto>.reviewsDtosToReviews() = map { it.reviewsDtoToReview() }