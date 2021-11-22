package by.nepravsky.data.network.repository

import by.nepravsky.domain.entity.base.OrderPrice
import by.nepravsky.domain.entity.request.PriceListRequest
import by.nepravsky.domain.entity.request.Settings

interface MarketerRepository {

    suspend fun request(request: PriceListRequest, settings: Settings): List<OrderPrice>
}