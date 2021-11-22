package by.nepravsky.data.database.repository


import by.nepravsky.domain.entity.base.Item
import by.nepravsky.domain.entity.request.ItemRequest
import by.nepravsky.domain.entity.request.Settings

interface TypeRepository {

    suspend fun getAllIds():List<Int>

    suspend fun get(itemRequest: ItemRequest, settings: Settings): Item


}