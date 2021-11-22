package by.nepravsky.domain.entity.base

data class OrderPrice(
    val itemId: Int,
    val systemId: Int,
    val regionId: Int,
    val sell: Double,
    val buy: Double,
    val lastUpdate: Long
)