package by.nepravsky.data.repoimpl

import by.nepravsky.data.database.AppDatabase
import by.nepravsky.data.database.entity.TypePriceSource
import by.nepravsky.domain.entity.base.PriceSource
import by.nepravsky.domain.repository.PriceSourceRepository

class PriceSourceRepoImpl(private val appDatabase: AppDatabase): PriceSourceRepository {

    override suspend fun getAll(): List<PriceSource> = appDatabase.typePriceSourceDao().getAll()
        .map{toDomain(it)}



    private fun toDomain(typePriceSource: TypePriceSource): PriceSource =
        PriceSource(
            typePriceSource.id,
            typePriceSource.name
        )
}