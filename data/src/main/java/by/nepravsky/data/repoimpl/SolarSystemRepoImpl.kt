package by.nepravsky.data.repoimpl

import by.nepravsky.data.database.AppDatabase
import by.nepravsky.data.database.entity.Systems
import by.nepravsky.domain.entity.base.SolarSystem
import by.nepravsky.domain.repository.SolarSystemsRepository

class SolarSystemRepoImpl(
    private val appDatabase: AppDatabase
): SolarSystemsRepository {

    override suspend fun getAll(): List<SolarSystem> =
        appDatabase.systemsDao().getAll()
            .map { toDomain(it) }

    private fun toDomain(system: Systems): SolarSystem =
        SolarSystem(
            system.id,
            system.name,
            system.regionId,
            system.regionName
        )

}