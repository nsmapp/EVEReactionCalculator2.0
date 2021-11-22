package by.nepravsky.domain.usecase.productline

import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.repository.ProjectRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.runFun
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetAllProjectsUseCase(private val projectRepository: ProjectRepository) {

    suspend fun getAll():Result<Flow<List<Project>>> =
        runFun { get() }

    private suspend fun get(): Flow<List<Project>> =
        withContext(IO){
            projectRepository.getAll()
        }
}