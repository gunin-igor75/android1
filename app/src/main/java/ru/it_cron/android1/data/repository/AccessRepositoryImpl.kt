package ru.it_cron.android1.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.it_cron.android1.data.model.DataResult
import ru.it_cron.android1.data.network.api.ApiService
import ru.it_cron.android1.domain.repository.AccessRepository
import java.io.IOException

class AccessRepositoryImpl(
    private val apiService: ApiService,
) : AccessRepository<Boolean> {
    override suspend fun checkAvailableCabinet(): DataResult<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.checkAvailableCabinet()
                DataResult.Success(response.dataAvailableDto.isCabinetAvailable)
            } catch (e: IOException) {
                Log.d(TAG, "Error ${e.message.toString()}")
                DataResult.ErrorInternet()
            } catch (e: HttpException) {
                Log.d(TAG, "Error ${e.message.toString()}")
                DataResult.Error(error = e.message.toString())
            }
        }
    }

    private companion object {
        const val TAG = "AccessRepositoryImpl"
    }
}