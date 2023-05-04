package by.nepravsky.data.database.repository

import by.nepravsky.data.database.AppDatabase
import by.nepravsky.data.database.entity.ProjectReactionItem
import by.nepravsky.data.database.entity.ProjectReactionItemWithName
import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.domain.entity.request.ProjectItemRequest
import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.entity.request.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReactionProjectItemDaoRepoImpl(
    private val appDatabase: AppDatabase
) : ReactionProjectItemDaoRepository {


    override fun getByParentIdFlow(
        projectRequest: ProjectRequest,
        settings: Settings
    ): Flow<List<ProjectItem>> = appDatabase.reactionProjectItemDao()
        .getByParentIdFlow(projectRequest.projectId)
        .map { it.map { t -> toDomain(t, settings) } }

    override fun getByParentId(
        projectRequest: ProjectRequest,
        settings: Settings
    ): List<ProjectItem> = appDatabase.reactionProjectItemDao()
        .getByParentId(projectRequest.projectId)
        .map { toDomain(it, settings) }

    override fun deleteByParentId(projectRequest: ProjectRequest): Int =
        appDatabase.reactionProjectItemDao().deleteByProjectId(projectRequest.projectId)

    override fun deleteById(projectItemRequest: ProjectItemRequest): Int =
        appDatabase.reactionProjectItemDao()
            .deleteById(projectItemRequest.itemId, projectItemRequest.projectId)

    override fun save(projectItem: ProjectItem): Long =
        appDatabase.reactionProjectItemDao().insert(toData(projectItem))

    private fun toData(item: ProjectItem): ProjectReactionItem =
        ProjectReactionItem(
            item.projectId,
            item.reactionId,
            item.run
        )

    private fun toDomain(
        item: ProjectReactionItemWithName,
        settings: Settings
    ): ProjectItem =
        ProjectItem(
            item.projectId,
            item.reactionId,
            item.run,
            item.getName(settings.languageId)
        )
}