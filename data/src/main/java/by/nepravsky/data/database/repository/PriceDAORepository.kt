package by.nepravsky.data.database.repository

import by.nepravsky.domain.entity.base.OrderPrice
import by.nepravsky.domain.entity.request.PriceListRequest
import by.nepravsky.domain.entity.request.Settings

interface PriceDAORepository {

    fun save(orderPrices: List<OrderPrice>): List<Long>
    fun needUpdate(request: PriceListRequest, settings: Settings): List<Int>
}