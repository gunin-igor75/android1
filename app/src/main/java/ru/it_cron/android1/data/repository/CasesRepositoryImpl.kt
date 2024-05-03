package ru.it_cron.android1.data.repository

import androidx.datastore.core.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.it_cron.android1.data.mappers.dataDtoCasesToCases
import ru.it_cron.android1.data.network.api.ApiService
import ru.it_cron.android1.domain.model.Case
import ru.it_cron.android1.domain.model.StateApp
import ru.it_cron.android1.domain.repository.CasesRepository

class CasesRepositoryImpl(
    private val apiService: ApiService,
) : CasesRepository<List<Case>> {

    override suspend fun getCases(): StateApp<List<Case>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCases()
                val casesDto = response.dataCasesDto
                val result = casesDto.dataDtoCasesToCases()
                StateApp.Success(
                    value = result
                )
            } catch (e: IOException) {
                StateApp.ErrorInternet()
            } catch (e: HttpException) {
                StateApp.Error(
                    error = e.message.toString()
                )
            }
        }
    }
}