package by.nepravsky.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.nepravsky.data.database.entity.Language.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Language(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: Int,
    @ColumnInfo(name = NAME)
    val name: String
) {
    companion object{
        const val TABLE_NAME = "lang"
        const val ID = "id"
        const val NAME = "name"

    }
}