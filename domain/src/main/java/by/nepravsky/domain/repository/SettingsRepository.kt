package by.nepravsky.domain.repository

import by.nepravsky.domain.entity.request.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun getSettings(): Flow<Settings>

    suspend fun save(settings: Settings): Long
}