package by.nepravsky.domain.usecase.productline

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.entity.request.ProjectItemRequest
import by.nepravsky.domain.repository.ProjectItemRepository
import by.nepravsky.domain.utils.runFunc

class DeleteProjectItemUseCase(
    private val projectItemRepository: ProjectItemRepository
) {

    suspend fun delete(projectItemRequest: ProjectItemRequest): Answer<Int> =
        runFunc { projectItemRepository.deleteById(projectItemRequest) }

}