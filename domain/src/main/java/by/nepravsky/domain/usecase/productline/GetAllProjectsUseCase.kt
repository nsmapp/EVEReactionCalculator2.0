package by.nepravsky.domain.usecase.productline

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.repository.ProjectRepository
import by.nepravsky.domain.utils.runFunc
import kotlinx.coroutines.flow.Flow

class GetAllProjectsUseCase(private val projectRepository: ProjectRepository) {

    suspend fun getAll(): Answer<Flow<List<Project>>> =
        runFunc { projectRepository.getAll() }

}