package ru.it_cron.intern1.presentation.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.it_cron.intern1.data.model.DataResult
import ru.it_cron.intern1.data.model.RequestApp
import ru.it_cron.intern1.domain.interactors.application.AreaActivityInteractor
import ru.it_cron.intern1.domain.interactors.application.BudgetInteractor
import ru.it_cron.intern1.domain.interactors.application.FileItemsInteractor
import ru.it_cron.intern1.domain.interactors.application.ServiceInteractor
import ru.it_cron.intern1.domain.model.app.AppItem
import ru.it_cron.intern1.domain.model.app.ContainerApp
import ru.it_cron.intern1.domain.model.app.FileItem
import ru.it_cron.intern1.domain.model.app.StateScreenApp
import ru.it_cron.intern1.domain.usecases.application.SendAppUseCase

class ApplicationViewModel(
    private val serviceInterActor: ServiceInteractor,
    private val budgetInterActor: BudgetInteractor,
    private val areaActivityInterActor: AreaActivityInteractor,
    private val fileItemsInterActor: FileItemsInteractor,
    private val sendAppUseCase: SendAppUseCase,
) : ViewModel() {
    val services: LiveData<List<AppItem>> = serviceInterActor.items().asLiveData()

    val budgets: LiveData<List<AppItem>> = budgetInterActor.items().asLiveData()

    val areaActivity: LiveData<List<AppItem>> = areaActivityInterActor.items().asLiveData()

    val fileItems: LiveData<List<FileItem>> = fileItemsInterActor.fileItems().asLiveData()

    val isFilesMaxCount: LiveData<Boolean> = fileItemsInterActor.isCountFiles().asLiveData()

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

    fun toggleService(resIdName: Int) {
        serviceInterActor.toggle(resIdName)
    }

    fun toggleBudget(resIdName: Int) {
        budgetInterActor.toggle(resIdName)
    }

    fun toggleAreaActivity(resIdName: Int) {
        areaActivityInterActor.toggle(resIdName)
    }

    fun clearChoices() {
        serviceInterActor.clearAll()
        budgetInterActor.clearAll()
        areaActivityInterActor.clearAll()
        fileItemsInterActor.clearFileItem()
    }

    fun addFileItem(fileItem: FileItem) {
        fileItemsInterActor.addFileItem(fileItem)
    }

    fun deleteFileItem(fileItem: FileItem) {
        fileItemsInterActor.deleteFileItem(fileItem)
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
        val service = serviceInterActor.selectedItems()
        val budget = budgetInterActor.selectedItems()
        val areaActivity = areaActivityInterActor.selectedItems()
        val files = fileItems.value ?: emptyList()
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
}