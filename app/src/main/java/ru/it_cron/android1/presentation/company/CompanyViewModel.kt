package ru.it_cron.android1.presentation.company

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.it_cron.android1.data.model.DataResult
import ru.it_cron.android1.domain.model.company.Review
import ru.it_cron.android1.domain.model.StateError
import ru.it_cron.android1.domain.usecases.GetReviewsUseCase

class CompanyViewModel(
    private val getReviewsUseCase: GetReviewsUseCase,
) : ViewModel() {

    private val errorChannel: Channel<StateError> = Channel()
    val error: Flow<StateError> = errorChannel.receiveAsFlow()

    private val _reviews: MutableLiveData<List<Review>> = MutableLiveData()
    val reviews: LiveData<List<Review>> = _reviews

    init {
        getReviews()
    }

    private fun getReviews() {
        viewModelScope.launch {
            when (val response = getReviewsUseCase()) {
                is DataResult.Error -> {
                    errorChannel.send(StateError.Error(response.error))
                }

                is DataResult.ErrorInternet -> {
                    errorChannel.send(StateError.ErrorInternet)
                }

                is DataResult.Success -> {
                    _reviews.value = response.value
                }
            }
        }
    }
}