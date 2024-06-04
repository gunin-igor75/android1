package ru.it_cron.intern1.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.it_cron.intern1.data.mappers.dataFiltersDtoToFiltersGroups
import ru.it_cron.intern1.data.model.DataResult
import ru.it_cron.intern1.data.network.api.ApiService
import ru.it_cron.intern1.domain.model.filter.FiltersGroup
import ru.it_cron.intern1.domain.repository.FilterRepository
import java.io.IOException

class FilterRepositoryImp(
    private val apiService: ApiService,
) : FilterRepository<List<FiltersGroup>> {

    override suspend fun getFilters(): DataResult<List<FiltersGroup>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getFilters()
                val result = response.dataFiltersDto.dataFiltersDtoToFiltersGroups()
                DataResult.Success(
                    value = result
                )
            } catch (e: IOException) {
                Log.d(TAG, "Error ${e.message.toString()}")
                DataResult.ErrorInternet()
            } catch (e: HttpException) {
                Log.d(TAG, "Error ${e.message.toString()}")
                DataResult.Error(
                    error = e.message.toString()
                )
            }
        }
    }

    private companion object {
        const val TAG = "FilterRepositoryImp"
    }
}