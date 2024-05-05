package ru.it_cron.android1.presentation.cases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.it_cron.android1.domain.model.Case
import ru.it_cron.android1.domain.model.StateApp
import ru.it_cron.android1.domain.usecases.GetCasesUseCase

class CasesViewModel(
    private val getCasesUseCase: GetCasesUseCase
): ViewModel() {

    private var _cases: MutableStateFlow<StateApp<List<Case>>> =
        MutableStateFlow(StateApp.Initial())
    val cases: StateFlow<StateApp<List<Case>>> = _cases.asStateFlow()

    init {
        getCases()
    }

    private fun getCases() {
        viewModelScope.launch {
            _cases.value = StateApp.Loading()
            val result = getCasesUseCase()
            _cases.value = result
        }
    }
}