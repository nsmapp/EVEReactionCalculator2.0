package by.nepravsky.domain.repository

import by.nepravsky.domain.entity.base.Item
import by.nepravsky.domain.entity.request.ItemRequest
import by.nepravsky.domain.entity.request.Settings

interface ItemRepository {

    suspend fun getAllItemIds():List<Int>

    suspend fun get(itemRequest: ItemRequest, settings: Settings): Item
}