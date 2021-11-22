package by.nepravsky.data.database.repository

import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.entity.request.ProjectRequest
import kotlinx.coroutines.flow.Flow

interface ReactionProjectDAORepository {

    suspend fun getAll(): Flow<List<Project>>
    suspend fun deleteById(projectRequest: ProjectRequest): Int
    suspend fun create(project: Project): Long
    suspend fun get(projectRequest: ProjectRequest): Project


}