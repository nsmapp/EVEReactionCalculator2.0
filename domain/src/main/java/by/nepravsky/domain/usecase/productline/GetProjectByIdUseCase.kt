package by.nepravsky.domain.usecase.productline

import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.repository.ProjectRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.runFun
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetProjectByIdUseCase(private val projectRepository: ProjectRepository) {

    suspend fun get(projectRequest: ProjectRequest):Result<Project> =
        runFun { getProject(projectRequest) }

    private suspend fun getProject(projectRequest: ProjectRequest): Project =
        withContext(IO){
            projectRepository.get(projectRequest)
        }
}