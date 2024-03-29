package by.nepravsky.domain.usecase

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.entity.base.OrderPrice
import by.nepravsky.domain.entity.domain.PriceSource
import by.nepravsky.domain.entity.request.*
import by.nepravsky.domain.repository.PriceRepository
import by.nepravsky.domain.repository.ReactionRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.runFun
import by.nepravsky.domain.utils.runFunc
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class UpdatePriceUseCase(
    private val reactionRepository: ReactionRepository,
    private val priceRepository: PriceRepository
) {


    suspend fun updatePrice(
        requests: List<ItemRequest>,
        settings: Settings
    ): Result<Unit> =
        withContext(IO) {
            runFun { update(requests, settings) }
        }

    suspend fun updatePrice(
        requests: ItemRequest,
        settings: Settings
    ): Answer<Unit> =
        withContext(IO) {
            runFunc { update(listOf(requests), settings) }
        }

    private fun update(
        requests: List<ItemRequest>,
        settings: Settings
    ) {

        val itemIdsSet = mutableSetOf<Int>()
        val tmpMaterialIds = mutableListOf<Int>()


        val reactions = reactionRepository.hasReactionFormula(requests, settings)
        itemIdsSet.addAll(reactions.map { it.products }.flatten().map { it.typeId })
        tmpMaterialIds.addAll(reactions.map { it.materials }.flatten().map { it.typeId })
        itemIdsSet.addAll(tmpMaterialIds)

        do {
            val requestIds = tmpMaterialIds.filter { it !in itemIdsSet }
            val subReactions = reactionRepository.hasReactionFormula(
                requestIds.map { ItemRequest(it) },
                settings
            )
            tmpMaterialIds.clear()
            itemIdsSet.addAll(subReactions.map { it.products }.flatten().map { it.typeId })
            tmpMaterialIds.addAll(subReactions.map { it.materials }.flatten().map { it.typeId })
            itemIdsSet.addAll(tmpMaterialIds)
        } while (subReactions.isNotEmpty())

        val itemsForUpdate = priceRepository
            .getItemIdsForUpdate(PriceListRequest(itemIdsSet.toList()), settings)

        if(itemsForUpdate.isEmpty()) return

        val orders: List<OrderPrice> = requestPrices(itemsForUpdate, settings)
        priceRepository.save(orders)
    }

    private fun requestPrices(
        itemIds: List<Int>,
        settings: Settings
    ): List<OrderPrice> =
        when (settings.priceUpdateSource) {
            PriceSource.EVE_TECH.id -> {
                val orderPrices = mutableListOf<OrderPrice>()
                itemIds.forEach { itemId ->
                    val order = priceRepository.get(
                        PriceRequest(itemId),
                        settings
                    )
                    orderPrices.add(order)
                }
                orderPrices
            }
            PriceSource.EVE_MARKETER.id -> {
                priceRepository.get(
                    PriceListRequest(itemIds),
                    settings
                )
            }
            else -> {
                listOf<OrderPrice>()
            }
        }

}