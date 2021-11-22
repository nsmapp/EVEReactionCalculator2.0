package by.nepravsky.domain.repository

import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.entity.request.ProjectRequest
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {

    suspend fun getAll(): Flow<List<Project>>

    suspend fun get(projectRequest: ProjectRequest): Project

    suspend fun create(project: Project): Long

    suspend fun deleteById(projectRequest: ProjectRequest): Int
}