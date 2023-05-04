package by.nepravsky.domain.usecase.productline

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.repository.ProjectItemRepository
import by.nepravsky.domain.repository.ProjectRepository
import by.nepravsky.domain.utils.runFunc

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val projectItemRepository: ProjectItemRepository
) {


    suspend fun delete(projectRequest: ProjectRequest): Answer<Int> =
        runFunc {
            projectItemRepository.deleteByParentId(projectRequest)
            projectRepository.deleteById(projectRequest)
        }
}