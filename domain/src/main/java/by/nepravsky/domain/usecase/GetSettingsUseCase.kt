package by.nepravsky.domain.usecase

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.repository.SettingsRepository
import by.nepravsky.domain.utils.runFunc
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetSettingsUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend fun get(): Answer<Flow<Settings>> =
        runFunc { settingsRepository.getSettings().flowOn(IO) }

}