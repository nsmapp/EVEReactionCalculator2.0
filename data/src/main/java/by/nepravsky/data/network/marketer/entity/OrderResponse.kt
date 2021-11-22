package by.nepravsky.data.network.marketer.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    @SerialName("forQuery")
    val orderPlace: OrderQueryResponse,
    @SerialName("max")
    val max: Double,
    @SerialName("min")
    val min: Double
)