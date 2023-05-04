package by.nepravsky.domain.usecase.productline

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.repository.ProjectItemRepository
import by.nepravsky.domain.utils.excepts.EmptyDateException
import by.nepravsky.domain.utils.runFunc
import kotlinx.coroutines.flow.Flow

class GetProjectsItemsUseCase(private val projectItemRepository: ProjectItemRepository) {

    suspend fun getByParentIdFlow(
        projectRequest: ProjectRequest,
        settings: Settings
    ): Answer<Flow<List<ProjectItem>>> =
        runFunc { projectItemRepository.getByParentIdFlow(projectRequest, settings) }

    suspend fun getByParentId(
        projectRequest: ProjectRequest,
        settings: Settings
    ): Answer<List<ProjectItem>> =
        runFunc {
            val result = projectItemRepository.getByParentId(projectRequest, settings)
            if (result.isEmpty()) throw EmptyDateException()
            result
        }
}