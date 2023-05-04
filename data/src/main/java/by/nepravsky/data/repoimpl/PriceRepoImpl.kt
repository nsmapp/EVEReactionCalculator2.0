package by.nepravsky.data.repoimpl

import by.nepravsky.data.database.repository.PriceDAORepository
import by.nepravsky.data.network.repository.EveTechRepository
import by.nepravsky.data.network.repository.MarketerRepository
import by.nepravsky.domain.entity.base.OrderPrice
import by.nepravsky.domain.entity.request.PriceListRequest
import by.nepravsky.domain.entity.request.PriceRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.repository.PriceRepository

class PriceRepoImpl(
    private val marketerRepository: MarketerRepository,
    private val eveTechRepository: EveTechRepository,
    private val priceDAORepository: PriceDAORepository,
): PriceRepository {


    override fun get(request: PriceRequest, settings: Settings): OrderPrice =
        eveTechRepository.request(request, settings)

    override fun get(request: PriceListRequest, settings: Settings): List<OrderPrice> =
        marketerRepository.request(request, settings)


    override fun save(orderPrices: List<OrderPrice>): List<Long> =
        priceDAORepository.save(orderPrices)

    override fun getItemIdsForUpdate(request: PriceListRequest, settings: Settings): List<Int> =
        priceDAORepository.needUpdate(request, settings)


}