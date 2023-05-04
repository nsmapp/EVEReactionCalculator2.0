package by.nepravsky.domain.usecase

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.entity.base.SolarSystem
import by.nepravsky.domain.repository.SolarSystemsRepository
import by.nepravsky.domain.utils.runFunc

class GetSolarSystemsUseCase(private val solarSystemsRepository: SolarSystemsRepository) {


    suspend fun getAll(): Answer<List<SolarSystem>> =
        runFunc { solarSystemsRepository.getAll() }

}