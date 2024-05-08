package ru.it_cron.android1.presentation.cases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.it_cron.android1.data.model.DataResult
import ru.it_cron.android1.domain.model.Case
import ru.it_cron.android1.domain.model.StateScreen
import ru.it_cron.android1.domain.usecases.GetCasesUseCase

class CasesViewModel(
    private val getCasesUseCase: GetCasesUseCase,
) : ViewModel() {

    private var _cases: MutableStateFlow<StateScreen<List<Case>>> =
        MutableStateFlow(StateScreen.Initial())
    val cases: StateFlow<StateScreen<List<Case>>> = _cases.asStateFlow()

    init {
        getCases()
    }

    private fun getCases() {
        viewModelScope.launch {
            _cases.value = StateScreen.Loading()
            val result = getCasesUseCase()
            when (result) {
                is DataResult.Error -> {
                    _cases.value = StateScreen.Error(result.error)
                }

                is DataResult.ErrorInternet -> {
                    _cases.value = StateScreen.ErrorInternet()
                }

                is DataResult.Success -> {
                    _cases.value = StateScreen.Success(result.value)
                }
            }
        }
    }
}