package by.nepravsky.domain.usecase.productline

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.domain.repository.ProjectItemRepository
import by.nepravsky.domain.utils.excepts.BrokenDateException
import by.nepravsky.domain.utils.runFunc

class SaveProjectItemUseCase(
    private val projectItemRepository: ProjectItemRepository
) {

    suspend fun save(projectItem: ProjectItem?): Answer<Long> =
        runFunc {
            if (projectItem == null) throw BrokenDateException()
            projectItemRepository.save(projectItem)
        }
}