package by.nepravsky.data.network.evetech.repoiml

import by.nepravsky.data.network.evetech.EveTechApi
import by.nepravsky.data.network.repository.EveTechRepository
import by.nepravsky.domain.entity.base.OrderPrice
import by.nepravsky.domain.entity.request.PriceRequest
import by.nepravsky.domain.entity.request.Settings
import java.util.*

class EveTechRepoImpl(
    private val eveTechApi: EveTechApi
): EveTechRepository {

    override fun request(priceRequest: PriceRequest, settings: Settings): OrderPrice {
        val prices = eveTechApi.getPricesAsync(settings.regionId, priceRequest.itemId)
        val sell = prices.filter { !it.isBuyOrder }.minOf { it.price }
        val buy = prices.filter { it.isBuyOrder }.maxOf { it.price }
        return OrderPrice(
            priceRequest.itemId,
            settings.systemId,
            settings.regionId,
            sell,
            buy,
            Date().time
        )
    }
}