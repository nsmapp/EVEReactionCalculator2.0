package by.nepravsky.domain.usecase.productline

import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.repository.ProjectItemRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.excepts.EmptyDateException
import by.nepravsky.domain.utils.runFun
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetProjectsItemsUseCase(private val projectItemRepository: ProjectItemRepository) {

    suspend fun getByParentIdFlow(
        projectRequest: ProjectRequest,
        settings: Settings
    ):Result<Flow<List<ProjectItem>>> =
        runFun { getFlow(projectRequest, settings) }

    private suspend fun getFlow(
        projectRequest: ProjectRequest,
        settings: Settings
    ): Flow<List<ProjectItem>> =
        withContext(IO){
            projectItemRepository.getByParentIdFlow(projectRequest, settings)
        }

    suspend fun getByParentId(
        projectRequest: ProjectRequest,
        settings: Settings
    ):Result<List<ProjectItem>> =
        runFun { get(projectRequest, settings) }

    private suspend fun get(
        projectRequest: ProjectRequest,
        settings: Settings
    ): List<ProjectItem> =
        withContext(IO){
            val result = projectItemRepository.getByParentId(projectRequest, settings)
            if (result.isEmpty()) throw  EmptyDateException()
            result
        }
}