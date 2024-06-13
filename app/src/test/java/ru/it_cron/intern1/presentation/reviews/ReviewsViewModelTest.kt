package ru.it_cron.intern1.presentation.reviews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test


class ReviewsViewModelTest {

    @JvmField
    @Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `видимость кнопки "Посмотреть еще" при длине списка менее 3`() {
        val viewModel = ReviewsViewModel(
            initialReviews = reviewsShort
        )

        val actual = viewModel.showMore.value

        assertThat(actual).isFalse()
    }

    @Test
    fun `видимость кнопки "Посмотреть еще" при длине списка более 3`() {
        val viewModel = ReviewsViewModel(
            initialReviews = reviewsLong
        )

        val actual = viewModel.showMore.value

        assertThat(actual).isTrue()
    }

    @Test
    fun `количество элементов в reviews при длине списка более 3 состояние isHideFullReviews`() {
        val viewModel = ReviewsViewModel(
            initialReviews = reviewsLong
        )

        val list1 = viewModel.reviews.value?: emptyList()
        val isHideFullReviewsList1 = viewModel.isHideFullReviews.value

        val size = list1.size

        assertThat(list1.size).isEqualTo(size)
        assertThat(isHideFullReviewsList1).isFalse()

        viewModel.addReview()

        val list2 = viewModel.reviews.value?: emptyList()
        val isHideFullReviewsList2 = viewModel.isHideFullReviews.value

        assertThat(list2.size).isEqualTo(size + 3)
        assertThat(isHideFullReviewsList2).isFalse()

        viewModel.addReview()

        val list3 = viewModel.reviews.value?: emptyList()
        val isHideFullReviewsList3 = viewModel.isHideFullReviews.value

        assertThat(list3.size).isEqualTo(size + 4)
        assertThat(isHideFullReviewsList3).isTrue()

        viewModel.resetReviews()

        val list4 = viewModel.reviews.value?: emptyList()
        val isHideFullReviewsList4 = viewModel.isHideFullReviews.value

        assertThat(list4.size).isEqualTo(size)
        assertThat(isHideFullReviewsList4).isFalse()
    }
}