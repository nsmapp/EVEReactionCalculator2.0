package by.nepravsky.data.database.repository

import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.domain.entity.request.ProjectItemRequest
import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.entity.request.Settings
import kotlinx.coroutines.flow.Flow

interface ReactionProjectItemDaoRepository {

    fun getByParentIdFlow(
        projectRequest: ProjectRequest,
        settings: Settings
    ): Flow<List<ProjectItem>>

    fun getByParentId(
        projectRequest: ProjectRequest,
        settings: Settings
    ): List<ProjectItem>

    fun deleteByParentId(projectRequest: ProjectRequest): Int
    fun deleteById(projectItemRequest: ProjectItemRequest): Int
    fun save(projectItem: ProjectItem): Long
}