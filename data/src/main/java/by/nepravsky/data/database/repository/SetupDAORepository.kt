package by.nepravsky.data.database.repository

import by.nepravsky.domain.entity.request.Settings
import kotlinx.coroutines.flow.Flow

interface SetupDAORepository {

    fun getSetup():Flow<Settings>

    fun save(settings: Settings): Long

}