package ru.it_cron.android1.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.it_cron.android1.data.mappers.reviewsDtosToReviews
import ru.it_cron.android1.data.model.DataResult
import ru.it_cron.android1.data.network.api.ApiService
import ru.it_cron.android1.domain.model.Review
import ru.it_cron.android1.domain.repository.ReviewsRepository
import java.io.IOException

class ReviewsRepositoryImpl(
    private val apiService: ApiService,
) : ReviewsRepository<List<Review>> {


    override suspend fun getReviews(): DataResult<List<Review>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getReviews()
                val result = response.reviews?.reviewsDtosToReviews() ?: emptyList()
                DataResult.Success(result)
            } catch (e: IOException) {
                Log.e(TAG, e.message.toString())
                DataResult.ErrorInternet()
            } catch (e: HttpException) {
                Log.e(TAG, e.message.toString())
                DataResult.Error(e.message.toString())
            }
        }
    }

    private companion object {
        const val TAG = "ReviewsRepositoryImpl"
    }
}