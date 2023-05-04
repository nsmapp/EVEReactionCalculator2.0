package by.nepravsky.data.repoimpl

import by.nepravsky.data.database.repository.ReactionProjectDAORepository
import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow

class ProjectRepoImpl(private val projectDAORepository: ReactionProjectDAORepository): ProjectRepository {

    override fun getAll(): Flow<List<Project>>  = projectDAORepository.getAll()

    override fun get(projectRequest: ProjectRequest): Project =
        projectDAORepository.get(projectRequest)

    override fun create(project: Project): Long =
        projectDAORepository.create(project)

    override fun deleteById(projectRequest: ProjectRequest): Int =
        projectDAORepository.deleteById(projectRequest)


}