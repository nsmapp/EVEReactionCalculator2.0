package by.nepravsky.domain.repository

import by.nepravsky.domain.entity.domain.ItemGroup
import by.nepravsky.domain.entity.request.Settings

interface ItemGroupRepository {

    suspend fun getAll(settings: Settings): List<ItemGroup>
}