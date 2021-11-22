package by.nepravsky.domain.usecase.productline

import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.repository.ProjectItemRepository
import by.nepravsky.domain.repository.ProjectRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.runFun
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val projectItemRepository: ProjectItemRepository
    ) {


    suspend fun delete(projectRequest: ProjectRequest): Result<Int> =
        runFun { deleteProject(projectRequest) }

    private suspend fun deleteProject(projectRequest: ProjectRequest): Int =
        withContext(Dispatchers.IO) {
            projectItemRepository.deleteByParentId(projectRequest)
            projectRepository.deleteById(projectRequest)
        }
}