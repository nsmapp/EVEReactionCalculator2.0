package by.nepravsky.domain.entity.base

data class ReactionItem(

    val typeId: Int,
    val groupId: Int,
    val volume: Double,
    val quantity : Int,
    val name: String,
    val basePrice: Double,
    val sell: Double,
    val buy: Double,
    val isCorrectElement: Boolean = true
){
    constructor(reactionFormulaElement: ReactionFormulaElement, item: Item)
            : this(
        typeId = reactionFormulaElement.typeId,
        groupId = item.groupId,
        volume = item.volume,
        quantity = reactionFormulaElement.quantity,
        name = item.name,
        basePrice = item.basePrice,
        sell = item.sell,
        buy = item.buy
    ) {


    }
}
