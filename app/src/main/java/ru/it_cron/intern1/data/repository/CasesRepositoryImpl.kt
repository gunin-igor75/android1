package ru.it_cron.intern1.data.repository

import android.util.Log
import androidx.datastore.core.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.it_cron.intern1.data.mappers.dataDtoCasesToCases
import ru.it_cron.intern1.data.model.DataResult
import ru.it_cron.intern1.data.network.api.ApiService
import ru.it_cron.intern1.domain.model.cases.Case
import ru.it_cron.intern1.domain.repository.CasesRepository

class CasesRepositoryImpl(
    private val apiService: ApiService,
) : CasesRepository<List<Case>> {

    private var cases: List<Case> = listOf()
    override suspend fun getCases(): DataResult<List<Case>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCases()
                val casesDto = response.dataCasesDto
                val result = casesDto.dataDtoCasesToCases()
                cases = result
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

    override fun getCasesStorage() = cases.toList()

    private companion object {
        const val TAG = "CasesRepositoryImpl"
    }
}