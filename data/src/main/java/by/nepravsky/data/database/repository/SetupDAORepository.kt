package by.nepravsky.data.database.repository

import by.nepravsky.domain.entity.request.Settings
import kotlinx.coroutines.flow.Flow

interface SetupDAORepository {

    suspend fun getSetup():Flow<Settings>

    suspend fun save(settings: Settings): Long

}