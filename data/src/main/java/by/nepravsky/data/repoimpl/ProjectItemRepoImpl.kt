package by.nepravsky.data.repoimpl

import by.nepravsky.data.database.repository.ReactionProjectItemDaoRepository
import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.domain.entity.request.ProjectItemRequest
import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.repository.ProjectItemRepository
import kotlinx.coroutines.flow.Flow

class ProjectItemRepoImpl(
    private val reactionProjectItemDaoRepository: ReactionProjectItemDaoRepository
): ProjectItemRepository {

    override suspend fun getByParentIdFlow(
        projectRequest: ProjectRequest,
        settings: Settings
    ): Flow<List<ProjectItem>> = reactionProjectItemDaoRepository
        .getByParentIdFlow(projectRequest, settings)

    override suspend fun getByParentId(
        projectRequest: ProjectRequest,
        settings: Settings
    ): List<ProjectItem> = reactionProjectItemDaoRepository
        .getByParentId(projectRequest, settings)

    override suspend fun deleteByParentId(projectRequest: ProjectRequest): Int =
        reactionProjectItemDaoRepository.deleteByParentId(projectRequest)

    override suspend fun deleteById(projectItemRequest: ProjectItemRequest): Int =
        reactionProjectItemDaoRepository.deleteById(projectItemRequest)

    override suspend fun save(projectItem: ProjectItem): Long =
        reactionProjectItemDaoRepository.save(projectItem)

}