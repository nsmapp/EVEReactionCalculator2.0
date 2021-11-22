package by.nepravsky.domain.entity.request

data class PriceListRequest(
    val itemIds: List<Int>,
    val updatePeriod: Long = 3600000
)