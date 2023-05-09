package by.nepravsky.sm.evereactioncalculator.presentation.recipe.createitem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.entity.domain.ItemGroup
import by.nepravsky.domain.entity.request.SearchReactionRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.usecase.group.GetItemGroupsUseCase
import by.nepravsky.domain.usecase.GetSettingsUseCase
import by.nepravsky.domain.usecase.SearchReactionUseCase
import by.nepravsky.domain.usecase.group.UpdateGroupSelectionUseCase
import by.nepravsky.domain.usecase.productline.SaveProjectItemUseCase
import by.nepravsky.domain.utils.excepts.BrokenDateException
import by.nepravsky.domain.utils.parseToInt
import by.nepravsky.sm.evereactioncalculator.utils.TEXT_EMPTY
import by.nepravsky.sm.evereactioncalculator.utils.events.Event
import by.nepravsky.sm.evereactioncalculator.utils.events.EventFinish
import by.nepravsky.sm.evereactioncalculator.utils.events.EventShowSnackBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemCreateViewModel(
    private val searchReactionUseCase: SearchReactionUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getAllGroupsUseCase: GetItemGroupsUseCase,
    private val saveProjectItemUseCase: SaveProjectItemUseCase,
    private val updateGroupSelectionUseCase: UpdateGroupSelectionUseCase
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
    private var searchText: String = TEXT_EMPTY

    init {
        searchReaction(searchText)
    }

    fun getSettings() {
        viewModelScope.launch {
            getSettingsUseCase.get().collect(Success = { flow ->
                flow.catch { Settings() }.collect { _settings.value = it }
            },
                Error = {})
        }
    }

    fun searchReaction(name: String) {
        showProgress()
        searchText = name
        viewModelScope.launch {
            searchReactionUseCase.get(SearchReactionRequest(name), settings.value)
                .collect(
                    Success = {
                        _reactions.value = it
                        stopProgress()
                    },
                    Error = { stopProgress() })
        }
    }

    fun saveProjectItem() {

        viewModelScope.launch {
            saveProjectItemUseCase.save(_projectItem.value).collect(
                Success = {
                    _eventList.emit(EventShowSnackBar("Saved"))
                    _eventList.emit(EventFinish())
                },
                Error = {
                    if (it is BrokenDateException) _eventList.emit(EventShowSnackBar("Reaction don't selected"))
                }
            )
        }
    }

    fun getGroups(settings: Settings) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                getAllGroupsUseCase.get(settings).collect(
                    Success = { _groups.value = it },
                    Error = {}
                )
            }
        }

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
                if (count >= 1) count else 1
            } ?: 1

            _projectItem.update { item ->
                item?.copy(run = run)
            }
        }
    }

    fun stopProgress() {
        _progress.value = false
    }

    fun showProgress() {
        _progress.value = true
    }

    fun updateGroupSelection(id: Int, selection: Boolean) {
        viewModelScope.launch {
            updateGroupSelectionUseCase.updateSelection(id, selection).collect(
                Success = { searchReaction(searchText) },
                Error = { searchReaction(searchText) }
            )
        }
    }

}