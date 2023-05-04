package by.nepravsky.domain.usecase

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.entity.base.ReactionFormulaElement
import by.nepravsky.domain.entity.base.ReactionItem
import by.nepravsky.domain.entity.domain.SubProduct
import by.nepravsky.domain.entity.presenter.ReactionInfo
import by.nepravsky.domain.entity.request.ItemRequest
import by.nepravsky.domain.entity.request.ReactionRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.repository.ItemRepository
import by.nepravsky.domain.repository.ReactionRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.runFun
import by.nepravsky.domain.utils.runFunc
import kotlin.math.ceil


class FullReactionUseCase(
    private val reactionRepository: ReactionRepository,
    private val itemRepository: ItemRepository
) {

    suspend fun run(
        reactionRequests: List<ReactionRequest>,
        settings: Settings
    ): Answer<ReactionInfo> = runFunc { makeReaction(reactionRequests, settings) }

    suspend fun run(
        reactionRequest: ReactionRequest,
        settings: Settings
    ): Answer<ReactionInfo> = runFunc { makeReaction(listOf(reactionRequest), settings) }

    private fun makeReaction(
        requests: List<ReactionRequest>,
        settings: Settings
    ): ReactionInfo {

        var products = mutableListOf<ReactionItem>()
        var baseMaterials = mutableListOf<ReactionItem>()

        val subMaterials = mutableListOf<ReactionItem>()


        requests.forEach { request ->

            val reaction = reactionRepository.get(request, settings)
            val productItems = reaction.products
                .map { element -> requestReactionItem(element, settings) }
                .map { multiRunProducts(request, it) }
            products.addAll(productItems)

            val materialItems = reaction.materials
                .map { element -> requestReactionItem(element, settings) }
                .map { multiRunMaterials(it, reaction, settings.me, request.run) }

            subMaterials.addAll(materialItems)
        }

        var subProduct = makeSubReaction(
            groupItem(subMaterials),
            settings
        )
        if (subProduct.complexMaterials.isEmpty()) baseMaterials.addAll(subMaterials)
        else {
            do {
                baseMaterials.addAll(subProduct.baseMaterials)
                subProduct = makeSubReaction(
                    subProduct.complexMaterials,
                    settings
                )
                if (subProduct.complexMaterials.isEmpty()) baseMaterials.addAll(subProduct.baseMaterials)
            } while (subProduct.complexMaterials.isNotEmpty())
        }

        products = groupItem(products)
        baseMaterials = groupItem(baseMaterials)

        return ReactionInfo(
            products.sortedBy { it.groupId },
            baseMaterials.sortedBy { it.groupId },
            baseMaterials.sumOf { it.sell },
            baseMaterials.sumOf { it.buy },
            baseMaterials.sumOf { it.volume },
            baseMaterials.sumOf { it.quantity },
            products.sumOf { it.sell },
            products.sumOf { it.buy },
            products.sumOf { it.volume },
            products.sumOf { it.quantity }
        )

    }

    private fun makeSubReaction(
        materials: List<ReactionItem>,
        settings: Settings
    ): SubProduct {

        var subMaterials = mutableListOf<ReactionItem>()

        materials.forEach { item ->
            val reactions = reactionRepository.hasReactionFormula(
                listOf(ItemRequest(item.typeId)),
                settings
            )
            if (reactions.isNotEmpty()) {
                val reaction = reactions[0]
                val productQnt = reaction.products.first { it.typeId == item.typeId }.quantity
                val minRun = ceil(item.quantity.toDouble() / productQnt.toDouble()).toInt()

                val materialItems = reaction.materials
                    .map { element -> requestReactionItem(element, settings) }
                    .map { multiRunMaterials(it, reaction, settings.subME, minRun) }
                subMaterials.addAll(materialItems)
            }
        }
        subMaterials = groupItem(subMaterials)

        return makeSubProduct(subMaterials, settings)
    }

    private fun makeSubProduct(
        items: List<ReactionItem>,
        settings: Settings
    ): SubProduct {
        val baseMaterials = mutableListOf<ReactionItem>()
        val complexMaterials = mutableListOf<ReactionItem>()
        val reactionIds = reactionRepository.hasReactionFormula(
            items.map { ItemRequest(it.typeId) },
            settings
        ).map { it.id }

        items.forEach { item ->
            if (item.typeId in reactionIds) complexMaterials.add(item)
            else baseMaterials.add(item)
        }

        return SubProduct(baseMaterials, complexMaterials)
    }


    private fun groupItem(items: List<ReactionItem>): MutableList<ReactionItem> =
        items.groupBy { it.typeId }
            .values
            .map { list ->
                ReactionItem(
                    list[0].typeId,
                    list[0].groupId,
                    list.sumOf { it.volume },
                    list.sumOf { it.quantity },
                    list[0].name,
                    list[0].basePrice,
                    list.sumOf { it.sell },
                    list.sumOf { it.buy },
                    list[0].isCorrectElement
                )
            }
            .toMutableList()


    private fun multiRunMaterials(
        item: ReactionItem,
        reaction: ReactionFormula,
        me: Int,
        run: Int
    ): ReactionItem {
        val dexME = if (reaction.isFormula) 1.0 else (100.0 - me) / 100.0
        val quantity: Int = (item.quantity * run * dexME).toInt()
        val volume = item.volume * quantity
        val sell = item.sell * quantity
        val buy = item.buy * quantity
        return ReactionItem(
            item.typeId, item.groupId,
            volume, quantity,
            item.name, item.basePrice,
            sell, buy, item.isCorrectElement
        )
    }

    private fun multiRunProducts(
        request: ReactionRequest,
        productItem: ReactionItem
    ): ReactionItem {

        val quantity = productItem.quantity * request.run
        val volume = productItem.volume * quantity
        val sell = productItem.sell * quantity
        val buy = productItem.buy * quantity
        return ReactionItem(
            productItem.typeId, productItem.groupId,
            volume, quantity,
            productItem.name, productItem.basePrice,
            sell, buy, productItem.isCorrectElement
        )
    }

    private fun requestReactionItem(
        element: ReactionFormulaElement,
        settings: Settings
    ): ReactionItem =
        try {
            ReactionItem(
                element,
                itemRepository.get(ItemRequest(element.typeId), settings)
            )
        } catch (e: Exception) {
            ReactionItem(
                element.typeId, -1, 0.0,
                0, "unknown item: ${element.typeId}",
                0.0, 0.0, 0.0, false
            )
        }


}