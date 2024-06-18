package ru.it_cron.intern1.presentation.cases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.it_cron.intern1.choice.ChoiceFilters
import ru.it_cron.intern1.choice.ChoiceState
import ru.it_cron.intern1.data.model.DataResult
import ru.it_cron.intern1.domain.model.StateError
import ru.it_cron.intern1.domain.model.StateScreen
import ru.it_cron.intern1.domain.model.cases.Case
import ru.it_cron.intern1.domain.usecases.GetCasesUseCase

class CasesViewModel(
    private val getCasesUseCase: GetCasesUseCase,
    private val choiceFilters: ChoiceFilters<String>,
) : ViewModel() {

    private var _state: MutableStateFlow<StateScreen<Boolean>> =
        MutableStateFlow(StateScreen.Initial())
    val state: StateFlow<StateScreen<Boolean>> = _state.asStateFlow()

    private var _cases: MutableStateFlow<MutableList<Case>> =
        MutableStateFlow(mutableListOf())

    private var _casesWithFilters = MutableLiveData<List<Case>>()
    val casesWithFilters: LiveData<List<Case>> = _casesWithFilters

    private var _isEnabled = MutableLiveData<Boolean>()
    val isEnabled: LiveData<Boolean> = _isEnabled

    private val errorChannel = Channel<StateError>()
    val error = errorChannel.receiveAsFlow()


    init {
        getCases()
        viewModelScope.launch {
            val combineFlow = combine(
                _cases,
                choiceFilters.stateIn(),
                ::merge
            )
            combineFlow.collect {
                _casesWithFilters.value = it
                _isEnabled.value = choiceFilters.isEnabled()
            }
        }
    }
    private fun getCases() {
        viewModelScope.launch {
            _state.value = StateScreen.Loading()
            when (val result = getCasesUseCase()) {
                is DataResult.Error -> {
                    errorChannel.send(StateError.Error(result.error))
                }

                is DataResult.ErrorInternet -> {
                    errorChannel.send(StateError.ErrorInternet)

                }

                is DataResult.Success -> {
                    _state.value = StateScreen.Success(true)
                    _cases.value = result.value.toMutableList()
                }
            }
        }
    }

    private fun merge(
        items: MutableList<Case>,
        choiceState: ChoiceState<String>,
    ): List<Case> {
        return if (choiceState.isEmpty()) {
            items.toList()
        } else {
            items.filter { case ->
                case.filters.any { filter ->
                    choiceState.isChecked(filter.id)
                }
            }
        }
    }

    fun clearFilter() {
        choiceFilters.clearAll()
    }
}