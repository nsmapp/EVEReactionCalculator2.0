package by.nepravsky.domain.usecase

import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.repository.SettingsRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.runFun
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SaveSettingsUseCase(private val settingsRepository: SettingsRepository) {



    suspend fun save(settings: Settings): Result<Long> =
        runFun{ saveSettings(settings) }


    private suspend fun saveSettings(settings: Settings): Long =
        withContext(IO){
            settingsRepository.save(settings)
        }
}