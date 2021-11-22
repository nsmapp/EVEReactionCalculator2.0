package by.nepravsky.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.nepravsky.data.database.entity.Type.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Type(
    @PrimaryKey
    @ColumnInfo(name = ID)          val id: Int,
    @ColumnInfo(name = BASE_PRICE)  val basePrice: Double,
    @ColumnInfo(name = GROUP_ID)    val groupId: Int,
    @ColumnInfo(name = VOLUME)      val volume: Double,
    @ColumnInfo(name = DE)    val de: String,
    @ColumnInfo(name = EN)    val en: String,
    @ColumnInfo(name = FR)    val fr: String,
    @ColumnInfo(name = JA)    val ja: String,
    @ColumnInfo(name = RU)    val ru: String,
    @ColumnInfo(name = ZH)    val zh: String
){
    companion object{
        const val TABLE_NAME: String = "type"
        const val ID: String = "id"
        const val BASE_PRICE: String = "base_price"
        const val GROUP_ID: String = "group_id"
        const val VOLUME: String = "volume"
        const val DE: String = "de"
        const val EN: String = "en"
        const val FR: String = "fr"
        const val JA: String = "ja"
        const val RU: String = "ru"
        const val ZH: String = "zh"
    }
}