package by.nepravsky.domain.repository

import by.nepravsky.domain.entity.base.OrderPrice
import by.nepravsky.domain.entity.request.PriceListRequest
import by.nepravsky.domain.entity.request.PriceRequest
import by.nepravsky.domain.entity.request.Settings


interface PriceRepository {

    suspend fun get(request: PriceRequest, settings: Settings): OrderPrice

    suspend fun get(request: PriceListRequest, settings: Settings): List<OrderPrice>

    suspend fun save(orderPrices: List<OrderPrice>): List<Long>

    suspend fun getItemIdsForUpdate(request: PriceListRequest, settings: Settings): List<Int>

}