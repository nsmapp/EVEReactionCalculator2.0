package by.nepravsky.domain.usecase.productline

import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.domain.repository.ProjectItemRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.excepts.BrokenDateException
import by.nepravsky.domain.utils.runFun
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveProjectItemUseCase(
    private val projectItemRepository: ProjectItemRepository) {


    suspend fun save(projectItem: ProjectItem?): Result<Long> =
        runFun { deleteProjectItem(projectItem) }

    private suspend fun deleteProjectItem(projectItem: ProjectItem?): Long =
        withContext(Dispatchers.IO) {
            if (projectItem == null) throw BrokenDateException()
            projectItemRepository.save(projectItem)
        }
}