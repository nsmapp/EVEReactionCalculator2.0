package by.nepravsky.domain.usecase.productline

import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.repository.ProjectRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.runFun
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveProjectUseCase(private val projectRepository: ProjectRepository, ) {


    suspend fun save(project: Project): Result<Long> =
        runFun { saveProject(project) }

    private suspend fun saveProject(project: Project): Long =
        withContext(Dispatchers.IO) {
            projectRepository.create(project)
        }
}