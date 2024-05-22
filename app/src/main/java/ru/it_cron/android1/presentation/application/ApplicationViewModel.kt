package ru.it_cron.android1.presentation.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope
import ru.it_cron.android1.choice.ChoiceAreaActivityDefault
import ru.it_cron.android1.choice.ChoiceBudgetDefault
import ru.it_cron.android1.choice.ChoiceServicesDefault
import ru.it_cron.android1.choice.ChoiceState
import ru.it_cron.android1.domain.model.app.AppItem
import ru.it_cron.android1.domain.usecases.application.GetAreaActivityUseCase
import ru.it_cron.android1.domain.usecases.application.GetBudgetsUseCase
import ru.it_cron.android1.domain.usecases.application.GetServicesUseCase

class ApplicationViewModel(
    private val getServicesUseCase: GetServicesUseCase,
    private val getBudgetsUseCase: GetBudgetsUseCase,
    private val getAreaActivityUseCase: GetAreaActivityUseCase,
) : ViewModel(), KoinScopeComponent {

    override val scope: Scope = createScope(this)
    private val choiceServices by scope.inject<ChoiceServicesDefault>()
    private val choiceBudget by scope.inject<ChoiceBudgetDefault>()
    private val choiceAreaActivity by scope.inject<ChoiceAreaActivityDefault>()


    private var _services = MutableLiveData<List<AppItem>>()
    val services: LiveData<List<AppItem>> = _services

    private var _budgets = MutableLiveData<List<AppItem>>()
    val budgets: LiveData<List<AppItem>> = _budgets

    private var _areaActivity = MutableLiveData<List<AppItem>>()
    val areaActivity: LiveData<List<AppItem>> = _areaActivity

    private var _serviceFlow: MutableStateFlow<MutableList<AppItem>> =
        MutableStateFlow(mutableListOf())

    private var _budgetFlow: MutableStateFlow<MutableList<AppItem>> =
        MutableStateFlow(mutableListOf())

    private var _areaActivityFlow: MutableStateFlow<MutableList<AppItem>> =
        MutableStateFlow(mutableListOf())

    init {
        getServices()
        getBudget()
        getAreaActivity()
        combineFlowService()
        combineFlowBudget()
        combineFlowAreaActivity()
    }

    private fun getServices() {
        viewModelScope.launch {
            getServicesUseCase().collect {
                _serviceFlow.value = it
            }
        }
    }

    private fun getBudget() {
        viewModelScope.launch {
            getBudgetsUseCase().collect {
                _budgetFlow.value = it
            }
        }
    }

    private fun getAreaActivity() {
        viewModelScope.launch {
            getAreaActivityUseCase().collect {
                _areaActivityFlow.value = it
            }
        }
    }

    private fun combineFlowService() {
        viewModelScope.launch {
            val combineFlow = combine(
                _serviceFlow,
                choiceServices.stateIn(),
                ::merge
            )
            combineFlow.collect {
                _services.value = it
            }
        }
    }

    private fun combineFlowBudget() {
        viewModelScope.launch {
            val combineFlow = combine(
                _budgetFlow,
                choiceBudget.stateIn(),
                ::merge
            )
            combineFlow.collect {
                _budgets.value = it
            }
        }
    }

    private fun combineFlowAreaActivity() {
        viewModelScope.launch {
            val combineFlow = combine(
                _areaActivityFlow,
                choiceAreaActivity.stateIn(),
                ::merge
            )
            combineFlow.collect {
                _areaActivity.value = it
            }
        }
    }

    fun toggleService(item: String) {
        choiceServices.toggle(item)
    }

    fun toggleBudget(item: String) {
        choiceBudget.toggle(item)
    }

    fun toggleAreaActivity(item: String) {
        choiceAreaActivity.toggle(item)
    }

    fun clearChoces() {
        choiceServices.clearAll()
        choiceBudget.clearAll()
        choiceAreaActivity.clearAll()
    }

    override fun onCleared() {
        super.onCleared()
        scope.close()
    }

    private fun merge(
        items: MutableList<AppItem>,
        choiceState: ChoiceState<String>,
    ): List<AppItem> {
        items.replaceAll { item ->
            if (item is AppItem.App) {
                item.copy(item.name, choiceState.isChecked(item.name))
            } else {
                item
            }
        }
        return items.toList()
    }
}