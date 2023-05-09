package by.nepravsky.sm.evereactioncalculator.presentation.project.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.nepravsky.domain.entity.domain.ItemGroup
import by.nepravsky.domain.entity.request.SearchReactionRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.usecase.group.GetItemGroupsUseCase
import by.nepravsky.domain.usecase.GetSettingsUseCase
import by.nepravsky.domain.usecase.SearchReactionUseCase
import by.nepravsky.domain.usecase.group.UpdateGroupSelectionUseCase
import by.nepravsky.sm.evereactioncalculator.presentation.project.projects.model.ProjectsState
import by.nepravsky.sm.evereactioncalculator.utils.TEXT_EMPTY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProjectsViewModel(
    private val searchReactionUseCase: SearchReactionUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getAllGroupsUseCase: GetItemGroupsUseCase,
    private val updateGroupSelectionUseCase: UpdateGroupSelectionUseCase
) : ViewModel() {

    private val _settings = MutableStateFlow(Settings())

    private val _state = MutableStateFlow<ProjectsState>(ProjectsState.Nothing)
    val state = _state.asStateFlow()

    private var searchText: String = TEXT_EMPTY

    init {
        searchReaction(searchText)
    }

    fun getSettings() {
        viewModelScope.launch {
            getSettingsUseCase.get().collect(
                Success = { flow ->
                    flow.catch { Settings() }.collect { handleUpdateSettings(it) }
                },
                Error = {}
            )
        }
    }

    private fun handleUpdateSettings(it: Settings) {
        _settings.value = it
        getGroups(it)
    }

    fun searchReaction(name: String) {
        showProgress()
        searchText = name
        viewModelScope.launch {
            searchReactionUseCase.get(SearchReactionRequest(name), _settings.value)
                .collect(
                    Success = {
                        _state.value = ProjectsState.UpdateFormulas(it)
                        stopProgress()
                    },
                    Error = { stopProgress() }
                )
        }
    }

    private fun getGroups(settings: Settings) {
        viewModelScope.launch {
            getAllGroupsUseCase.get(settings).collect(
                Success = {
                    _state.value = ProjectsState.UpdateGroup(it)
                },
                Error = {
                }
            )
        }
    }


    fun stopProgress() {
        _state.value = ProjectsState.ShowProgress(false)
    }

    private fun showProgress() {
        _state.value = ProjectsState.ShowProgress(true)
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