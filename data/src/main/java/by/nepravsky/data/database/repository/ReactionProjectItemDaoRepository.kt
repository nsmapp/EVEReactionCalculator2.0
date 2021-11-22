package by.nepravsky.data.database.repository

import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.domain.entity.request.ProjectItemRequest
import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.entity.request.Settings
import kotlinx.coroutines.flow.Flow

interface ReactionProjectItemDaoRepository {

    suspend fun getByParentIdFlow(
        projectRequest: ProjectRequest,
        settings: Settings
    ): Flow<List<ProjectItem>>

    suspend fun getByParentId(
        projectRequest: ProjectRequest,
        settings: Settings
    ): List<ProjectItem>

    suspend fun deleteByParentId(projectRequest: ProjectRequest): Int
    suspend fun deleteById(projectItemRequest: ProjectItemRequest): Int
    suspend fun save(projectItem: ProjectItem): Long
}