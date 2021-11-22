package by.nepravsky.domain.usecase.productline

import by.nepravsky.domain.entity.request.ProjectItemRequest
import by.nepravsky.domain.repository.ProjectItemRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.runFun
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteProjectItemUseCase(
    private val projectItemRepository: ProjectItemRepository) {


    suspend fun delete(projectItemRequest: ProjectItemRequest): Result<Int> =
        runFun { deleteProjectItem(projectItemRequest) }

    private suspend fun deleteProjectItem(projectItemRequest: ProjectItemRequest): Int =
        withContext(Dispatchers.IO) {
            projectItemRepository.deleteById(projectItemRequest)
        }
}