package by.nepravsky.data.database.repository

import by.nepravsky.data.database.AppDatabase
import by.nepravsky.data.database.entity.Setup
import by.nepravsky.domain.entity.request.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SetupDAOImpl(
    private val appDatabase: AppDatabase
) : SetupDAORepository {


    override fun getSetup(): Flow<Settings> = appDatabase.setupDao()
        .getSetup()
        .map { toDomain(it) }

    override fun save(settings: Settings): Long = appDatabase.setupDao().insert(toData(settings))


    private fun toData(settings: Settings): Setup =
        Setup(
            settings.languageId,
            settings.systemId,
            settings.regionId,
            settings.me,
            settings.te,
            settings.subME,
            settings.subTE,
            settings.priceUpdateSource,
            1
        )

    private fun toDomain(setup: Setup): Settings =
        Settings(
            setup.languageId,
            setup.systemId,
            setup.regionId,
            setup.me,
            setup.te,
            setup.subME,
            setup.subTE,
            setup.priceUpdateSource
        )
}