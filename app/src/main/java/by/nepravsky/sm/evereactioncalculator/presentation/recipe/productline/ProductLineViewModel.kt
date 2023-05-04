package by.nepravsky.sm.evereactioncalculator.presentation.recipe.productline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.domain.entity.request.ProjectItemRequest
import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.usecase.GetSettingsUseCase
import by.nepravsky.domain.usecase.productline.DeleteProjectItemUseCase
import by.nepravsky.domain.usecase.productline.GetProjectByIdUseCase
import by.nepravsky.domain.usecase.productline.GetProjectsItemsUseCase
import by.nepravsky.domain.usecase.productline.SaveProjectUseCase
import by.nepravsky.domain.utils.Result
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
            getSettingsUseCase.get().collect(
                Success = { flow -> flow.catch { Settings() }.collect { _settings.value = it } },
                Error = {}
            )
        }
    }

    fun getProject() {
        viewModelScope.launch {
            getProjectByIdUseCase.get(ProjectRequest(projectId)).collect(
                Success = { _project.value = it },
                Error = {}
            )
        }
    }

    fun getProjectItems() {
        viewModelScope.launch {
            val request = getProjectsItemsUseCase.getByParentIdFlow(
                ProjectRequest(projectId), _settings.value
            )
            request.collect(
                Success = { flow ->
                    flow.catch { listOf<ProjectItem>() }.collect { _items.value = it }
                },
                Error = {}
            )
        }
    }

    fun deleteProjectItem(projectItem: ProjectItem) {
        viewModelScope.launch {
            deleteProjectItemUseCase.delete(
                ProjectItemRequest(projectItem.reactionId, projectItem.projectId)
            ).collect(
                Success = {},
                Error = {}
            )
        }
    }

    fun saveNewProject(name: String, description: String) {
        if (projectId == -1) return
        viewModelScope.launch {
            withContext(IO) {
                saveProjectUseCase.save(Project(projectId, name, description))
                    .collect(
                        Success = {},
                        Error = {}
                    )
            }
        }
    }


}