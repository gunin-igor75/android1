package ru.it_cron.android1.presentation.filter

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
import ru.it_cron.android1.choice.ChoiceFilters
import ru.it_cron.android1.choice.ChoiceState
import ru.it_cron.android1.data.model.DataResult
import ru.it_cron.android1.domain.model.filter.FiltersGroup
import ru.it_cron.android1.domain.model.StateError
import ru.it_cron.android1.domain.model.StateScreen
import ru.it_cron.android1.domain.model.filter.TypeItem
import ru.it_cron.android1.domain.usecases.GetFiltersUseCase

class FiltersViewModel(
    private val getFiltersUseCase: GetFiltersUseCase,
    private val choiceFilters: ChoiceFilters<String>,
) : ViewModel() {

    private var _state: MutableStateFlow<StateScreen<Boolean>> =
        MutableStateFlow(StateScreen.Initial())
    val state: StateFlow<StateScreen<Boolean>> = _state.asStateFlow()

    private var _filters: MutableStateFlow<MutableList<TypeItem>> =
        MutableStateFlow(mutableListOf())

    private var _typeItems = MutableLiveData<List<TypeItem>>()
    val typeItems: LiveData<List<TypeItem>> = _typeItems

    private var _isEnabled = MutableLiveData<Boolean>()
    val isEnabled: LiveData<Boolean> = _isEnabled

    private val errorChannel = Channel<StateError>()
    val error = errorChannel.receiveAsFlow()


    init {
        getFilters()
        viewModelScope.launch {
            val combineFlow = combine(
                _filters,
                choiceFilters.stateIn(),
                ::merge
            )
            combineFlow.collect {
                _typeItems.value = it
                _isEnabled.value = choiceFilters.isEnabled()
            }
        }
    }

    private fun getFilters() {
        viewModelScope.launch {
            _state.value = StateScreen.Loading()
            when (val result = getFiltersUseCase()) {
                is DataResult.Error -> {
                    errorChannel.send(StateError.Error(result.error))
                }

                is DataResult.ErrorInternet -> {
                    errorChannel.send(StateError.ErrorInternet)

                }

                is DataResult.Success -> {
                    _state.value = StateScreen.Success(true)
                    _filters.value = convertToTypeItem(result.value)

                }
            }
        }
    }

    fun clearFilter() {
        choiceFilters.clearAll()
    }

    fun toggle(filterId: String) {
        choiceFilters.toggle(filterId)
    }

    private fun merge(
        items: MutableList<TypeItem>,
        choiceState: ChoiceState<String>,
    ): List<TypeItem> {
        items.replaceAll { item ->
            if (item is TypeItem.FilterItem) {
                item.copy(item.id, item.name, choiceState.isChecked(item.id))
            } else {
                item
            }
        }
        return items.toList()
    }

    private fun convertToTypeItem(filtersGroup: List<FiltersGroup>): MutableList<TypeItem> {
        val result = mutableListOf<TypeItem>()
        filtersGroup.map {
            result.add(TypeItem.Header(it.id, it.name))
            it.filters.forEach { filter ->
                result.add(TypeItem.FilterItem(filter.id, filter.name, false))
            }
        }
        return result
    }
}