package by.nepravsky.domain.entity.base

class Item(
    val id: Int,
    val groupId: Int,
    val volume: Double,
    val name: String,
    val basePrice: Double,
    val sell: Double,
    val buy: Double
)