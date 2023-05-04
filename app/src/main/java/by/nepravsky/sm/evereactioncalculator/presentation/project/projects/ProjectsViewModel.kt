package by.nepravsky.sm.evereactioncalculator.presentation.project.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.entity.domain.ItemGroup
import by.nepravsky.domain.entity.request.SearchReactionRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.usecase.GetItemGroupsUseCase
import by.nepravsky.domain.usecase.GetSettingsUseCase
import by.nepravsky.domain.usecase.SearchReactionUseCase
import by.nepravsky.sm.evereactioncalculator.presentation.project.projects.model.ProjectsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProjectsViewModel(
    private val searchReactionUseCase: SearchReactionUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getAllGroupsUseCase: GetItemGroupsUseCase
) : ViewModel() {

    private val _settings = MutableStateFlow(Settings())

    private val _state = MutableStateFlow<ProjectsState>(ProjectsState.Nothing)
    val state = _state.asStateFlow()

    private val selectedGroups = mutableListOf<ItemGroup>()


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
        viewModelScope.launch {
            searchReactionUseCase.get(SearchReactionRequest(name, selectedGroups), _settings.value)
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

    fun setSelectedItemGroups(groups: List<ItemGroup>) {
        selectedGroups.clear()
        selectedGroups.addAll(groups)
    }

    fun stopProgress() {
        _state.value = ProjectsState.ShowProgress(false)
    }

    fun showProgress() {
        _state.value = ProjectsState.ShowProgress(true)
    }
}