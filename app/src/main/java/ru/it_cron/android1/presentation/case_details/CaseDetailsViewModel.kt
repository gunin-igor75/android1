package ru.it_cron.android1.presentation.case_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.it_cron.android1.data.model.DataResult
import ru.it_cron.android1.domain.model.CaseDetails
import ru.it_cron.android1.domain.model.StateError
import ru.it_cron.android1.domain.model.StateScreen
import ru.it_cron.android1.domain.usecases.GetCaseDetailsUseCase

class CaseDetailsViewModel(
    private val getCaseDetailsUseCase: GetCaseDetailsUseCase,
) : ViewModel() {

    private var _caseDetails: MutableStateFlow<StateScreen<CaseDetails>> =
        MutableStateFlow(StateScreen.Initial())
    val caseDetails: StateFlow<StateScreen<CaseDetails>> = _caseDetails.asStateFlow()

    private val errorChannel = Channel<StateError>()
    val error = errorChannel.receiveAsFlow()


    fun getCase(caseId: String) {
        viewModelScope.launch {
            _caseDetails.value = StateScreen.Loading()
            when (val result = getCaseDetailsUseCase(caseId)) {
                is DataResult.Error -> {
                    errorChannel.send(StateError.Error(result.error))
                }

                is DataResult.ErrorInternet -> {
                    errorChannel.send(StateError.ErrorInternet)

                }

                is DataResult.Success -> {
                    _caseDetails.value = StateScreen.Success(result.value)
                }
            }
        }
    }
}