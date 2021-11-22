package by.nepravsky.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.nepravsky.data.database.entity.Systems.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class Systems(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: Int,

    @ColumnInfo(name = NAME)
    val name: String,

    @ColumnInfo(name = REGION_ID)
    val regionId: Int,

    @ColumnInfo(name = REGION_NAME)
    val regionName: String
){
    companion object{
        const val TABLE_NAME = "systems"
        const val ID: String = "system_id"
        const val NAME: String = "system_name"
        const val REGION_ID: String = "region_id"
        const val REGION_NAME: String = "region_name"

    }
}