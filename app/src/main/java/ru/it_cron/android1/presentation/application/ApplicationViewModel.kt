package ru.it_cron.android1.presentation.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.it_cron.android1.choice.ChoiceFilters
import ru.it_cron.android1.choice.ChoiceState
import ru.it_cron.android1.data.model.DataResult
import ru.it_cron.android1.data.model.RequestApp
import ru.it_cron.android1.domain.model.app.AppItem
import ru.it_cron.android1.domain.model.app.ContainerApp
import ru.it_cron.android1.domain.model.app.FileItem
import ru.it_cron.android1.domain.model.app.StateScreenApp
import ru.it_cron.android1.domain.usecases.application.AddFileItemUseCase
import ru.it_cron.android1.domain.usecases.application.ClearFileItemsUseCase
import ru.it_cron.android1.domain.usecases.application.DeleteFileItemUseCase
import ru.it_cron.android1.domain.usecases.application.GetAreaActivityUseCase
import ru.it_cron.android1.domain.usecases.application.GetBudgetsUseCase
import ru.it_cron.android1.domain.usecases.application.GetFileItemsUseCase
import ru.it_cron.android1.domain.usecases.application.GetServicesUseCase
import ru.it_cron.android1.domain.usecases.application.IsCountFilesUseCase
import ru.it_cron.android1.domain.usecases.application.SendAppUseCase
import ru.it_cron.android1.presentation.extension.mutableLiveDataIn
import ru.it_cron.android1.presentation.extension.mutableStateIn

class ApplicationViewModel(
    private val deleteFileItemUseCase: DeleteFileItemUseCase,
    private val addFileItemUseCase: AddFileItemUseCase,
    private val sendAppUseCase: SendAppUseCase,
    private val clearFileItemsUseCase: ClearFileItemsUseCase,
    private val choiceServices: ChoiceFilters<String>,
    private val choiceBudget: ChoiceFilters<String>,
    private val choiceAreaActivity: ChoiceFilters<String>,
    getServicesUseCase: GetServicesUseCase,
    getBudgetsUseCase: GetBudgetsUseCase,
    getAreaActivityUseCase: GetAreaActivityUseCase,
    getFileItemsUseCase: GetFileItemsUseCase,
    isCountFilesUseCase: IsCountFilesUseCase,
) : ViewModel() {


    private var _services = MutableLiveData<List<AppItem>>()
    val services: LiveData<List<AppItem>> = _services

    private var _budgets = MutableLiveData<List<AppItem>>()
    val budgets: LiveData<List<AppItem>> = _budgets

    private var _areaActivity = MutableLiveData<List<AppItem>>()
    val areaActivity: LiveData<List<AppItem>> = _areaActivity

    private var _serviceFlow: MutableStateFlow<MutableList<AppItem>> =
        getServicesUseCase().mutableStateIn(
            viewModelScope,
            mutableListOf()
        )

    private var _budgetFlow: MutableStateFlow<MutableList<AppItem>> =
        getBudgetsUseCase().mutableStateIn(
            viewModelScope,
            mutableListOf()
        )

    private var _areaActivityFlow: MutableStateFlow<MutableList<AppItem>> =
        getAreaActivityUseCase().mutableStateIn(
            viewModelScope,
            mutableListOf()
        )

    private var _fileItems: MutableLiveData<List<FileItem>> =
        getFileItemsUseCase().mutableLiveDataIn(viewModelScope)
    val fileItems: LiveData<List<FileItem>> = _fileItems

    private var _isFilesMaxCount: MutableLiveData<Boolean> =
        isCountFilesUseCase().mutableLiveDataIn(viewModelScope)
    val isFilesMaxCount: LiveData<Boolean> = _isFilesMaxCount

    private var _isFileMaxSize: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFileNaxSize: LiveData<Boolean> = _isFileMaxSize

    private val _servicesState = MutableStateFlow(false)
    private val _budgetState = MutableStateFlow(false)
    private val _areaActivityState = MutableStateFlow(false)
    private val _taskState = MutableStateFlow(false)
    private val _nameState = MutableStateFlow(false)
    private val _companyState = MutableStateFlow(false)
    private val _emailState = MutableStateFlow(false)
    private val _phoneState = MutableStateFlow(false)
    private val _personalInfoState = MutableStateFlow(false)
    private val _politicState = MutableStateFlow(false)
    val buttonSendAppIsActive = combine(
        _servicesState,
        _budgetState,
        _areaActivityState,
        _taskState,
        _nameState,
        _companyState,
        _emailState,
        _phoneState,
        _personalInfoState,
        _politicState,
    ) { array -> array.all { it } }

    private val answerChannel = Channel<StateScreenApp>()
    val answer = answerChannel.receiveAsFlow()

    init {
        combineFlowService()
        combineFlowBudget()
        combineFlowAreaActivity()
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

    fun clearChoices() {
        choiceServices.clearAll()
        choiceBudget.clearAll()
        choiceAreaActivity.clearAll()
        clearFileItemsUseCase()
    }

    fun addFileItem(fileItem: FileItem) {
        addFileItemUseCase(fileItem)
    }

    fun deleteFileItem(fileItem: FileItem) {
        deleteFileItemUseCase(fileItem)
    }

    fun setTaskState(value: Boolean) {
        _taskState.value = value
    }

    fun setNameState(value: Boolean) {
        _nameState.value = value
    }

    fun setCompanyState(value: Boolean) {
        _companyState.value = value
    }

    fun setEmailState(value: Boolean) {
        _emailState.value = value
    }

    fun setPhoneState(value: Boolean) {
        _phoneState.value = value
    }

    fun setPersonalInfoState(value: Boolean) {
        _personalInfoState.value = value
    }

    fun setPoliticState(value: Boolean) {
        _politicState.value = value
    }

    fun sendApp(containerApp: ContainerApp) {
        val service = choiceServices.getEnabledId().joinToString("\n")
        val budget = choiceBudget.getEnabledId().first()
        val areaActivity = choiceAreaActivity.getEnabledId().first()
        val files = _fileItems.value ?: emptyList()
        val requestApp = RequestApp(
            task = containerApp.task,
            name = containerApp.name,
            company = containerApp.company,
            email = containerApp.email,
            phone = containerApp.phone,
            services = service,
            budget = budget,
            areaActivity = areaActivity,
            files = files
        )
        viewModelScope.launch {
            when (sendAppUseCase(requestApp)) {
                is DataResult.Error -> {
                    answerChannel.send(StateScreenApp.Error)
                }

                is DataResult.ErrorInternet -> {
                    answerChannel.send(StateScreenApp.ErrorInternet)
                }

                is DataResult.Success -> {
                    answerChannel.send(StateScreenApp.Success)
                }
            }
        }
    }

    fun isFileMaxSize(value: Boolean) {
        _isFileMaxSize.value = value
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
                _servicesState.value = !choiceServices.isEmpty()
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
                _budgetState.value = !choiceBudget.isEmpty()
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
                _areaActivityState.value = !choiceAreaActivity.isEmpty()
            }
        }
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