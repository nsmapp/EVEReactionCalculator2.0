package by.nepravsky.domain.usecase

import by.nepravsky.domain.entity.base.SolarSystem
import by.nepravsky.domain.repository.SolarSystemsRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.runFun
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetSolarSystemsUseCase(private val solarSystemsRepository: SolarSystemsRepository) {


   suspend fun getAll():Result<List<SolarSystem>> =
       runFun { get() }


    private suspend fun get(): List<SolarSystem> =
        withContext(IO){
            solarSystemsRepository.getAll()
        }

}