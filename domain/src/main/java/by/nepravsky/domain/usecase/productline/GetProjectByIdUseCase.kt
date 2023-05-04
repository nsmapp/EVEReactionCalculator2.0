package by.nepravsky.domain.usecase.productline

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.repository.ProjectRepository
import by.nepravsky.domain.utils.runFunc

class GetProjectByIdUseCase(private val projectRepository: ProjectRepository) {

    suspend fun get(projectRequest: ProjectRequest): Answer<Project> =
        runFunc { projectRepository.get(projectRequest) }

}