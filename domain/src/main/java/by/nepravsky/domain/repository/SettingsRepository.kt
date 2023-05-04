package by.nepravsky.domain.repository

import by.nepravsky.domain.entity.request.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettings(): Flow<Settings>

    fun save(settings: Settings): Long
}