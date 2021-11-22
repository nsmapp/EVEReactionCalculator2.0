package by.nepravsky.data.network.repository

import by.nepravsky.domain.entity.base.OrderPrice
import by.nepravsky.domain.entity.request.PriceRequest
import by.nepravsky.domain.entity.request.Settings

interface EveTechRepository {

    suspend fun request(priceRequest: PriceRequest, settings: Settings): OrderPrice
}