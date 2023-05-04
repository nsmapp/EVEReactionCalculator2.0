package by.nepravsky.domain.usecase

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.repository.SettingsRepository
import by.nepravsky.domain.utils.runFunc

class SaveSettingsUseCase(private val settingsRepository: SettingsRepository) {


    suspend fun save(settings: Settings): Answer<Long> =
        runFunc { settingsRepository.save(settings) }
}