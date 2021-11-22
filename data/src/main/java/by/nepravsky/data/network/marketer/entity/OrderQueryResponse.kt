package by.nepravsky.data.network.marketer.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderQueryResponse(
    @SerialName("types")
    val typeIds: List<Int>,
    @SerialName("regions")
    val regions: List<Int>,
    @SerialName("systems")
    val systems: List<Int>
)