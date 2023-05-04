package by.nepravsky.data.database.repository

import by.nepravsky.data.database.AppDatabase
import by.nepravsky.data.database.entity.ProjectReaction
import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.entity.request.ProjectRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReactionProjectDAORepoImpl(private val appDatabase: AppDatabase): ReactionProjectDAORepository {

    override fun getAll(): Flow<List<Project>> =
        appDatabase.reactionProjectDao().getAll()
            .map { it.map { t -> toDomain(t) } }

    override fun deleteById(projectRequest: ProjectRequest): Int =
        appDatabase.reactionProjectDao().deleteById(projectRequest.projectId)

    override fun create(project: Project): Long =
        appDatabase.reactionProjectDao().insert(toData(project))

    override fun get(projectRequest: ProjectRequest): Project{
        val result = appDatabase.reactionProjectDao().getById(projectRequest.projectId)
        return toDomain(result)
    }


    private fun toData(project: Project): ProjectReaction =
        ProjectReaction(
            project.id,
            project.name,
            project.description
        )

    private fun toDomain(projectReaction: ProjectReaction):  Project =
        Project(
            projectReaction.id,
            projectReaction.name,
            projectReaction.description
        )
}