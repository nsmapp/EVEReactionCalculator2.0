package by.nepravsky.sm.evereactioncalculator.presentation.recipe.reactor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.usecase.productline.DeleteProjectUseCase
import by.nepravsky.domain.usecase.productline.GetAllProjectsUseCase
import by.nepravsky.domain.usecase.productline.SaveProjectUseCase
import by.nepravsky.domain.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
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



    fun getProjects(){
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                when(val request = getAllProjectsUseCase.getAll()){
                    is Result.Success-> {
                        request.data
                            .catch { emptyList<Project>() }
                            .collect { _projects.value = it }

                    }
                    is  Result.Error ->{}
                }
            }
        }
    }

    fun deleteProject(projectId: Int){
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                when(val request = deleteProjectUseCase.delete(ProjectRequest(projectId))){
                    is Result.Success-> { }
                    is  Result.Error ->{}
                }
            }
        }
    }

    fun createNewProject(){
        viewModelScope.launch {
            withContext(IO){
                val request = saveProjectUseCase.save(
                    Project(0,"Project #${Date().time}", "")
                )
                when(request){
                    is Result.Success -> {
                        _newProjectId.value = request.data
                    }
                    is Result.Error -> {}
                }
            }
        }
    }

    fun newProjectCreated() {
        _newProjectId.value = -1
    }

}