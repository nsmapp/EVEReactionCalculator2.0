package by.nepravsky.data.database.repository

import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.entity.request.ProjectRequest
import kotlinx.coroutines.flow.Flow

interface ReactionProjectDAORepository {

    fun getAll(): Flow<List<Project>>
    fun deleteById(projectRequest: ProjectRequest): Int
    fun create(project: Project): Long
    fun get(projectRequest: ProjectRequest): Project


}