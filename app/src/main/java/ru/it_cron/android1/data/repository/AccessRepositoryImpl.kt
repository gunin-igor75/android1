package ru.it_cron.android1.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.it_cron.android1.data.network.api.ApiService
import ru.it_cron.android1.domain.model.AvailableState
import ru.it_cron.android1.domain.repository.AccessRepository
import java.io.IOException

class AccessRepositoryImpl(
    private val apiService: ApiService
) : AccessRepository {
    override suspend fun checkAvailableCabinet(): AvailableState {
        return withContext(Dispatchers.IO){
            try {
                val response = apiService.checkAvailableCabinet()
                if (response.error == null) {
                    AvailableState.Success(response.dataAvailableDto.isCabinetAvailable)
                } else {
                    AvailableState.Error(response.error)
                }
            } catch (e: IOException){
                Log.d(TAG, "Error ${e.message.toString()}")
                AvailableState.Error(
                    "Error  ${e.message}"
                )
            } catch (e: HttpException){
                Log.d(TAG, "Error ${e.message.toString()}")
                AvailableState.Error("Error ${e.message}")
            }
        }
    }

    private companion object{
        const val TAG = "AccessRepository"
    }
}