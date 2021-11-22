package by.nepravsky.sm.evereactioncalculator.presentation.recipe.productline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.domain.entity.request.ProjectItemRequest
import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.usecase.GetSettingsUseCase
import by.nepravsky.domain.usecase.productline.*
import by.nepravsky.domain.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductLineViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getProjectsItemsUseCase: GetProjectsItemsUseCase,
    private val deleteProjectItemUseCase: DeleteProjectItemUseCase,
    private val getProjectByIdUseCase: GetProjectByIdUseCase,
    private val saveProjectUseCase: SaveProjectUseCase
) : ViewModel() {

    var projectId = -1

    private val _settings = MutableStateFlow(Settings())
    val settings = _settings.asStateFlow()
    private val _items = MutableStateFlow(emptyList<ProjectItem>())
    val items = _items.asStateFlow()
    private val _project = MutableStateFlow<Project?>(null)
    val project = _project.asStateFlow()


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

    fun getProject() {
        viewModelScope.launch {
            withContext(IO) {
                val request = getProjectByIdUseCase.get(ProjectRequest(projectId))
                when (request) {
                    is Result.Success -> {
                        _project.value = request.data
                    }
                    is Result.Error -> {
                    }
                }
            }
        }
    }

    fun getProjectItems() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                val request = getProjectsItemsUseCase.getByParentIdFlow(
                    ProjectRequest(projectId), _settings.value
                )
                when (request) {
                    is Result.Success -> {
                        request.data
                            .catch { listOf<ProjectItem>() }
                            .collect { _items.value = it }

                    }
                    is Result.Error -> {
                    }
                }
            }
        }
    }

    fun deleteProjectItem(projectItem: ProjectItem) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                val request = deleteProjectItemUseCase
                    .delete(ProjectItemRequest(projectItem.reactionId, projectItem.projectId))
                when (request) {
                    is Result.Success -> {
                    }
                    is Result.Error -> {
                    }
                }
            }
        }
    }

    fun saveNewProject(name: String, description: String) {
        if (projectId == -1) return
        viewModelScope.launch {
            withContext(IO) {
                val request = saveProjectUseCase.save(
                    Project(projectId, name, description)
                )
                when (request) {
                    is Result.Success -> {
                    }
                    is Result.Error -> {
                    }
                }
            }
        }
    }


}