package by.nepravsky.domain.repository

import by.nepravsky.domain.entity.domain.ItemGroup
import by.nepravsky.domain.entity.request.Settings

interface ItemGroupRepository {

    fun getAll(settings: Settings): List<ItemGroup>

    fun updateSelection(id: Int, isSelection: Boolean)
}