package by.nepravsky.data.network.marketer.repoimpl

import by.nepravsky.data.network.marketer.MarketerApi
import by.nepravsky.data.network.marketer.entity.MarketerPriceResponse
import by.nepravsky.data.network.repository.MarketerRepository
import by.nepravsky.domain.entity.base.OrderPrice
import by.nepravsky.domain.entity.request.PriceListRequest
import by.nepravsky.domain.entity.request.Settings
import java.util.*

class MarketerRepoImpl(
    private val marketerApi: MarketerApi
): MarketerRepository {


    override suspend fun request(request: PriceListRequest, settings: Settings): List<OrderPrice> {
        val chunkedTypeIds = if (request.itemIds.size > 190) request.itemIds.chunked(190)
                                else listOf(request.itemIds)

        val result = mutableListOf<MarketerPriceResponse>()
        chunkedTypeIds.forEach {
            result.addAll(marketerApi
                .getPricesAsync(it, settings.systemId)
                .await()
            )
        }
        return result.map { toDomain(it, settings) }
    }

    private fun toDomain(
        marketerPriceResponse: MarketerPriceResponse,
        settings: Settings,
        date: Long = Date().time
    ): OrderPrice {
        val place = marketerPriceResponse.sell.orderPlace
        return OrderPrice(
            place.typeIds[0],
            settings.systemId,
            settings.regionId,
            marketerPriceResponse.sell.min,
            marketerPriceResponse.buy.max,
            date
        )



}


}