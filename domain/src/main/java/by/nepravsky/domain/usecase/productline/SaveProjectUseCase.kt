package by.nepravsky.domain.usecase.productline

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.repository.ProjectRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.runFun
import by.nepravsky.domain.utils.runFunc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveProjectUseCase(private val projectRepository: ProjectRepository) {


    suspend fun save(project: Project): Answer<Long> =
        runFunc { projectRepository.create(project) }

}