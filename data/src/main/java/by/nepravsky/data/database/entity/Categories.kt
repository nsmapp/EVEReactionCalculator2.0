package by.nepravsky.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.nepravsky.data.database.entity.Categories.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Categories(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: Int,
    @ColumnInfo(name = NAME)
    val name: String
){
    companion object{

        const val TABLE_NAME: String = "type_categories"
        const val ID: String = "id"
        const val NAME: String = "name"
    }
}