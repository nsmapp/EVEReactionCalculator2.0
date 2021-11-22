package by.nepravsky.data.network.marketer.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data  class MarketerPriceResponse(
    @SerialName("buy")
    val buy: OrderResponse,
    @SerialName("sell")
    val sell: OrderResponse
)