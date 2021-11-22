package by.nepravsky.data.database.repository

import by.nepravsky.data.database.AppDatabase
import by.nepravsky.data.database.entity.TypeWithPrice
import by.nepravsky.domain.entity.base.Item
import by.nepravsky.domain.entity.request.ItemRequest
import by.nepravsky.domain.entity.request.Settings

class TypeRepoImpl(
    private val appDatabase: AppDatabase
    ): TypeRepository {

    override suspend fun getAllIds(): List<Int> =
        appDatabase.typeDao().getIds()

    override suspend fun get(itemRequest: ItemRequest, settings: Settings): Item =
        toDomain(
            appDatabase.typeDao().get(itemRequest.itemId, settings.systemId),
            settings
        )

    private fun toDomain(type: TypeWithPrice, settings: Settings): Item =
        Item(
            type.id,
            type.groupId,
            type.volume,
            type.getName(settings.languageId),
            type.basePrice,
            type.sell,
            type.buy
        )
}