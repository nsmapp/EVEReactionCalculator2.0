package by.nepravsky.sm.evereactioncalculator.presentation.recipe.reactor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.usecase.productline.DeleteProjectUseCase
import by.nepravsky.domain.usecase.productline.GetAllProjectsUseCase
import by.nepravsky.domain.usecase.productline.SaveProjectUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class ReactorViewModel(
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val saveProjectUseCase: SaveProjectUseCase
) : ViewModel() {


    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects = _projects.asStateFlow()

    private val _newProjectId = MutableStateFlow<Long>(-1)
    val newProjectId = _newProjectId.asStateFlow()


    fun getProjects() {
        viewModelScope.launch {
            getAllProjectsUseCase.getAll().collect(
                Success = { flow ->
                    flow.catch { emptyList<Project>() }.collect { _projects.value = it }
                },
                Error = {}
            )
        }
    }

    fun deleteProject(projectId: Int) {
        viewModelScope.launch {
            deleteProjectUseCase.delete(ProjectRequest(projectId)).collect(
                Success = {},
                Error = {}
            )
        }
    }

    fun createNewProject() {
        viewModelScope.launch {
            withContext(IO) {
                saveProjectUseCase.save(Project(0, "Project #${Date().time}", ""))
                    .collect(
                        Success = { _newProjectId.value = it },
                        Error = {}
                    )
            }
        }
    }

    fun newProjectCreated() {
        _newProjectId.value = -1
    }

}