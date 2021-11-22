
import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.entity.base.ReactionFormulaElement
import by.nepravsky.domain.entity.presenter.ReactionInfo
import by.nepravsky.domain.entity.base.ReactionItem
import by.nepravsky.domain.entity.request.ItemRequest
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.runFun
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

import by.nepravsky.domain.entity.request.ReactionRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.repository.ItemRepository
import by.nepravsky.domain.repository.ReactionRepository
import kotlin.math.roundToInt

class BaseReactionUseCase(
    private val reactionRepository: ReactionRepository,
    private val itemRepository: ItemRepository
) {

    suspend fun run(
        reactionRequests: List<ReactionRequest>,
        settings: Settings
    ): Result<ReactionInfo> =
        withContext(IO){
            runFun { makeReaction(reactionRequests, settings) }
        }

    suspend fun run(
        reactionRequest: ReactionRequest,
        settings: Settings
    ): Result<ReactionInfo> =
        withContext(IO){
            runFun { makeReaction(listOf(reactionRequest), settings) }
        }


    private suspend fun makeReaction(
        reactionRequests: List<ReactionRequest>,
        settings: Settings
    ): ReactionInfo {
        var products = mutableListOf<ReactionItem>()
        var materials = mutableListOf<ReactionItem>()

        reactionRequests.forEach { job ->
            val reaction = reactionRepository.get(job, settings)
            val productItems = reaction.products
                .map { element ->  requestReactionItem(element, settings)}
                .map { multiRunProducts(job, it)}
            products.addAll(productItems)


            val materialItems = reaction.materials
                .map { element ->  requestReactionItem(element, settings)}
                .map { multiRunMaterials( it, reaction, settings.me, job.run)}
            materials.addAll(materialItems)
        }

        products = groupItem(products)
        materials = groupItem(materials)

        return ReactionInfo(
            products,
            materials,
            materials.sumOf { it.sell },
            materials.sumOf { it.buy },
            materials.sumOf { it.volume },
            materials.sumOf { it.quantity },
            products.sumOf { it.sell } ,
            products.sumOf { it.buy } ,
            products.sumOf { it.volume } ,
            products.sumOf { it.quantity }
        )
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
            }.toMutableList()


    private fun multiRunMaterials(
        materialItem: ReactionItem,
        reaction: ReactionFormula,
        me: Int,
        run: Int
    ): ReactionItem {

        val dexME: Double = if (reaction.isFormula) 1.0 else (100.0 - me) / 100.0
        val quantity: Int = (materialItem.quantity * run * dexME).roundToInt()
        val volume = materialItem.volume * quantity
        val sell = materialItem.sell * quantity
        val buy = materialItem.buy * quantity
        return ReactionItem(
            materialItem.typeId,materialItem.groupId,
            volume, quantity,
            materialItem.name, materialItem.basePrice,
            sell, buy, materialItem.isCorrectElement
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
            productItem.typeId,productItem.groupId,
            volume, quantity,
            productItem.name, productItem.basePrice,
            sell, buy, productItem.isCorrectElement
        )
    }

    private suspend fun requestReactionItem(
        element: ReactionFormulaElement,
        settings: Settings
    ): ReactionItem =
        try {
            ReactionItem(
                element,
                itemRepository.get(ItemRequest(element.typeId), settings)
            )
        }catch (e: Exception){
            ReactionItem(
                element.typeId, -1, 0.0,
                0, "unknown item: ${element.typeId}",
                0.0, 0.0,0.0, false
            )
        }




}