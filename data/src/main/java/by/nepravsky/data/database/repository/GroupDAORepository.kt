package by.nepravsky.data.database.repository

import by.nepravsky.domain.entity.domain.ItemGroup
import by.nepravsky.domain.entity.request.Settings

interface GroupDAORepository {

    suspend fun getAll(settings: Settings): List<ItemGroup>
}