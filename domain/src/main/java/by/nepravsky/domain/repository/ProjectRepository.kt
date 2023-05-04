package by.nepravsky.domain.repository

import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.entity.request.ProjectRequest
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {

    fun getAll(): Flow<List<Project>>

    fun get(projectRequest: ProjectRequest): Project

    fun create(project: Project): Long

    fun deleteById(projectRequest: ProjectRequest): Int
}