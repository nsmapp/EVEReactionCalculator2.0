package by.nepravsky.data.network.evetech.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data  class EveTechPriceResponse(
    @SerialName("is_buy_order")
    val isBuyOrder: Boolean,
    @SerialName("price")
    val price: Double,
    @SerialName("type_id")
     val typeId: Int
)