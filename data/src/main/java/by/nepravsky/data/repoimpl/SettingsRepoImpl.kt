package by.nepravsky.data.repoimpl

import by.nepravsky.data.database.repository.SetupDAORepository
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepoImpl(
    private val  setupDAORepository: SetupDAORepository
):SettingsRepository {
    override suspend fun getSettings(): Flow<Settings> = setupDAORepository.getSetup()

    override suspend fun save(settings: Settings): Long = setupDAORepository.save(settings)

}