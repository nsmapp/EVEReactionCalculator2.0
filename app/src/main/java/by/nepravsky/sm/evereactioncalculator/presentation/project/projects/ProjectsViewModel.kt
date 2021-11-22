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
import by.nepravsky.domain.utils.Result
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProjectsViewModel(
    private val searchReactionUseCase: SearchReactionUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getAllGroupsUseCase: GetItemGroupsUseCase
) : ViewModel() {

    private val _settings = MutableStateFlow(Settings())
    val settings = _settings.asStateFlow()
    private val _reactions = MutableStateFlow<List<ReactionFormula>>(emptyList())
    val reactions: StateFlow<List<ReactionFormula>> = _reactions.asStateFlow()
    private val _groups = MutableStateFlow<List<ItemGroup>>(emptyList())
    val group: StateFlow<List<ItemGroup>> = _groups.asStateFlow()
    private val _progress = MutableStateFlow(false)
    val progress = _progress.asStateFlow()

    private val selectedGroups = mutableListOf<ItemGroup>()


    fun getSettings() {
        viewModelScope.launch {
            withContext(Main) {
                when (val setup = getSettingsUseCase.get()) {
                    is Result.Success -> {
                        setup.data
                            .catch { Settings() }
                            .collect { _settings.value = it }
                    }
                    is Result.Error -> {
                        stopProgress()
                    }
                }
            }
        }
    }

    fun searchReaction(name: String) {
        showProgress()
        viewModelScope.launch {
            withContext(Main) {
                val reactions = searchReactionUseCase.get(
                    SearchReactionRequest(name, selectedGroups), settings.value
                )
                when (reactions) {
                    is Result.Success -> {
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

    fun getGroups(settings: Settings) {
        viewModelScope.launch {
            withContext(Main) {
                val groups = getAllGroupsUseCase.get(settings)
                when (groups) {
                    is Result.Success -> {
                        _groups.value = groups.data
                    }
                    is Result.Error -> {}
                }
            }
        }

    }

    fun setSelectedItemGroups(groups: List<ItemGroup>) {
        selectedGroups.clear()
        selectedGroups.addAll(groups)
    }

    fun stopProgress() {
        _progress.value = false
    }

    fun showProgress() {
        _progress.value = true
    }
}