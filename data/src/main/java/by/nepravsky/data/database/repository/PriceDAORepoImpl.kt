package by.nepravsky.data.database.repository

import by.nepravsky.data.database.AppDatabase
import by.nepravsky.data.database.entity.Price
import by.nepravsky.domain.entity.base.OrderPrice
import by.nepravsky.domain.entity.request.PriceListRequest
import java.util.*

class PriceDAORepoImpl(
    private val appDatabase: AppDatabase
): PriceDAORepository {


    override suspend fun save(orderPrices: List<OrderPrice>): List<Long>{
        val prices = orderPrices.map { toData(it) }
        return appDatabase.priceDao().insert(prices)
    }

    override suspend fun needUpdate(request: PriceListRequest): List<Int> =
        appDatabase.priceDao().get(request.itemIds, Date().time - request.updatePeriod)

    private fun toData(order: OrderPrice): Price =
        Price(order.itemId, order.systemId, order.regionId, order.sell, order.buy, order.lastUpdate)
}