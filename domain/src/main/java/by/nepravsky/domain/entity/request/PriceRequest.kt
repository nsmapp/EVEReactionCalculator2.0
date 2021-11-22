package by.nepravsky.domain.entity.request

data class PriceRequest(
    val itemId: Int,
    val updatePeriod: Long = 36000
)