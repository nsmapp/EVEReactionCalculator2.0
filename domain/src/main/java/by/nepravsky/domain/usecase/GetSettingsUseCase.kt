package by.nepravsky.domain.usecase

import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.repository.SettingsRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.runFun
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetSettingsUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend fun get(): Result<Flow<Settings>> =
            runFun { settingsRepository.getSettings().flowOn(IO) }

}