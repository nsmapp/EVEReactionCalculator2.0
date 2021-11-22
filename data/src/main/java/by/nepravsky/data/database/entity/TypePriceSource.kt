package by.nepravsky.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.nepravsky.data.database.entity.TypePriceSource.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class TypePriceSource(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: Int,
    @ColumnInfo(name = NAME)
    val name: String
) {
    companion object{
        const val TABLE_NAME = "price_source"
        const val ID = "id"
        const val NAME = "name"
    }
}