package by.nepravsky.data.database.typeconverter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import by.nepravsky.data.database.entity.ReactionElement
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ProvidedTypeConverter
class ReactionElementConverter {

    @TypeConverter
    fun toList(json: String): List<ReactionElement> =
        Json.decodeFromString(json)

    @TypeConverter
    fun toString(reactionElement: List<ReactionElement>): String =
        Json.encodeToString(reactionElement)

}