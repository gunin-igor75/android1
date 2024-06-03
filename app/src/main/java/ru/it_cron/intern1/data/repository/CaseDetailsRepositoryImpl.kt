package ru.it_cron.intern1.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.it_cron.intern1.data.mappers.dataDetailsCaseDtoToCaseDetails
import ru.it_cron.intern1.data.model.DataResult
import ru.it_cron.intern1.data.network.api.ApiService
import ru.it_cron.intern1.domain.model.cases.CaseDetails
import ru.it_cron.intern1.domain.repository.CaseDetailsRepository
import java.io.IOException

class CaseDetailsRepositoryImp(
    private val apiService: ApiService,
) : CaseDetailsRepository<CaseDetails> {
    override suspend fun getCase(caseId: String): DataResult<CaseDetails> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCase(caseId)
                val caseDetailsDto = response.dataDetailsCaseDto
                val result = caseDetailsDto.dataDetailsCaseDtoToCaseDetails()
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
        const val TAG = "CaseDetailsRepositoryImp"
    }
}