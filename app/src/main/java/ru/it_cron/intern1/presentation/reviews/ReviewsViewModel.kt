package ru.it_cron.intern1.presentation.reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.it_cron.intern1.domain.model.company.Review

class ReviewsViewModel(
    private val initialReviews: List<Review>,
) : ViewModel() {

    private val _showMore: MutableLiveData<Boolean> = MutableLiveData(false)
    val showMore: LiveData<Boolean> = _showMore

    private val _isHideFullReviews: MutableLiveData<Boolean> = MutableLiveData(false)
    val isHideFullReviews: LiveData<Boolean> = _isHideFullReviews

    private var _reviewsData: MutableList<Review> = mutableListOf()
    private val reviewsData: List<Review>
        get() = _reviewsData.toList()

    private val _reviews: MutableLiveData<List<Review>> = MutableLiveData()
    val reviews: LiveData<List<Review>> = _reviews

    init {
        showMoreIsVisible()
        setupReviews()
        updateIsHideFullReviews()
    }

    fun addReview() {
        val beginIndex = _reviewsData.size
        val virtualSize = beginIndex + SIZE
        val endIndex = if (initialReviews.size < virtualSize) initialReviews.size else virtualSize
        val tmpList = initialReviews.subList(beginIndex, endIndex)
        _reviewsData.addAll(tmpList)
        _reviews.value = reviewsData
        updateIsHideFullReviews()
    }

    fun resetReviews() {
        setupReviews()
        updateIsHideFullReviews()
    }

    private fun showMoreIsVisible() {
        val isVisible = initialReviews.size > SIZE || initialReviews.isEmpty()
        _showMore.value = isVisible
    }

    private fun setupReviews() {
        val index = if (initialReviews.size < SIZE) initialReviews.size % SIZE else SIZE
        _reviewsData = initialReviews.subList(0, index).toMutableList()
        _reviews.value = reviewsData
    }

    private fun updateIsHideFullReviews() {
        val isVisible = checkIsHideFullReviews()
        _isHideFullReviews.value = isVisible
    }

    private fun checkIsHideFullReviews(): Boolean {
        return initialReviews.size > SIZE && reviewsData.size == initialReviews.size
    }

    companion object {
        private const val SIZE = 3
    }
}
