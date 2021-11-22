package by.nepravsky.sm.evereactioncalculator.presentation.recipe.createitem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.entity.domain.ItemGroup
import by.nepravsky.domain.entity.request.SearchReactionRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.usecase.GetItemGroupsUseCase
import by.nepravsky.domain.usecase.GetSettingsUseCase
import by.nepravsky.domain.usecase.SearchReactionUseCase
import by.nepravsky.domain.usecase.productline.SaveProjectItemUseCase
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.excepts.BrokenDateException
import by.nepravsky.domain.utils.parseToInt
import by.nepravsky.sm.evereactioncalculator.utils.events.Event
import by.nepravsky.sm.evereactioncalculator.utils.events.EventFinish
import by.nepravsky.sm.evereactioncalculator.utils.events.EventShowSnackBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemCreateViewModel(
    private val searchReactionUseCase: SearchReactionUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getAllGroupsUseCase: GetItemGroupsUseCase,
    private val saveProjectItemUseCase: SaveProjectItemUseCase
) : ViewModel() {

    private val _settings = MutableStateFlow(Settings())
    val settings = _settings.asStateFlow()
    private val _reactions = MutableStateFlow<List<ReactionFormula>>(emptyList())
    val reactions: StateFlow<List<ReactionFormula>> = _reactions.asStateFlow()
    private val _groups = MutableStateFlow<List<ItemGroup>>(emptyList())
    val group: StateFlow<List<ItemGroup>> = _groups.asStateFlow()
    private val _projectItem = MutableStateFlow<ProjectItem?>(null)
    var projectItem = _projectItem.asStateFlow()
    private val _eventList = MutableSharedFlow<Event>()
    val eventList = _eventList.asSharedFlow()
    private val _progress = MutableStateFlow(false)
    val progress = _progress.asStateFlow()

    private var projectId = -1
    private val selectedGroups = mutableListOf<ItemGroup>()

    fun getSettings() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                when (val setup = getSettingsUseCase.get()) {
                    is Result.Success -> {
                        setup.data
                            .catch { Settings() }
                            .collect { _settings.value = it }
                    }
                    is Result.Error -> {
                    }
                }
            }
        }
    }

    fun searchReaction(name: String) {
        showProgress()
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                val reactions = searchReactionUseCase.get(
                    SearchReactionRequest(name, selectedGroups), settings.value
                )
                when (reactions) {
                    is Result.Success ->{
                        _reactions.value = reactions.data
                        stopProgress()
                    }
                    is Result.Error -> {
                        stopProgress()
                    }
                }
            }
        }
    }

    fun saveProjectItem() {

        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                    val request = saveProjectItemUseCase.save(_projectItem.value)
                    when (request) {
                        is Result.Success -> {
                            _eventList.emit(EventShowSnackBar("Saved"))
                            _eventList.emit(EventFinish())
                        }
                        is Result.Error -> {
                            if (request.exception is BrokenDateException)
                                _eventList.emit(EventShowSnackBar("Reaction don't selected"))
                        }
                    }
            }
        }
    }

    fun getGroups(settings: Settings) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                val groups = getAllGroupsUseCase.get(settings)
                when (groups) {
                    is Result.Success -> {
                        _groups.value = groups.data
                    }
                    is Result.Error -> {
                    }
                }
            }
        }

    }

    fun setSelectedItemGroups(groups: List<ItemGroup>) {
        selectedGroups.clear()
        selectedGroups.addAll(groups)
    }

    fun initProjectItem(itemId: Int, runs: Int, name: String, projectId: Int) {
        _projectItem.value = ProjectItem(projectId, itemId, runs, name)
        this.projectId = projectId
    }


    fun changeRuns(charSequence: CharSequence?) {
        viewModelScope.launch(Dispatchers.IO) {
            _projectItem.update { item ->
                item?.copy(run = charSequence.parseToInt(1))
            }
        }
    }

    fun upRun() {
        viewModelScope.launch(Dispatchers.IO) {
            _projectItem.update { item ->
                item?.copy(run = (item.run + 1))
            }
        }
    }

    fun downRun() {
        viewModelScope.launch(Dispatchers.IO) {
            val run = _projectItem.value?.let {
                val count = it.run - 1
                if (count >= 0) count else 1
            } ?: 1

            _projectItem.update { item ->
                item?.copy(run = run)
            }
        }
    }

    fun stopProgress(){
        _progress.value = false
    }

    fun showProgress(){
        _progress.value = true
    }

}