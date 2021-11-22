package by.nepravsky.data.repoimpl

import by.nepravsky.data.database.repository.TypeRepository
import by.nepravsky.domain.entity.base.Item
import by.nepravsky.domain.entity.request.ItemRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.repository.ItemRepository

class ItemRepoImpl(private val typeRepository: TypeRepository): ItemRepository {


    override suspend fun getAllItemIds(): List<Int> =
        typeRepository.getAllIds()

    override suspend fun get(itemRequest: ItemRequest, settings: Settings): Item =
        typeRepository.get(itemRequest, settings)


}