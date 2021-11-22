package by.nepravsky.data.database.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReactionElement(
    @SerialName("quantity")
    val quantity : Int,
    @SerialName("typeID")
    val typeID : Int
)
