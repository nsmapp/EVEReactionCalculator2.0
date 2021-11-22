package by.nepravsky.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import by.nepravsky.data.database.entity.Price.Companion.SYSTEM_ID
import by.nepravsky.data.database.entity.Price.Companion.TABLE_NAME
import by.nepravsky.data.database.entity.Price.Companion.TYPE_ID

@Entity(tableName = TABLE_NAME, primaryKeys = [SYSTEM_ID, TYPE_ID])
data class Price(

    @ColumnInfo(name = TYPE_ID)
    val typeId: Int,

    @ColumnInfo(name = SYSTEM_ID)
    val systemId :Int,

    @ColumnInfo(name = REGION_ID)
    val regionId :Int,

    @ColumnInfo(name = SELL)
    val sell :Double,

    @ColumnInfo(name = BUY)
    val buy :Double,

    @ColumnInfo(name = UPDATE_TIME)
    val lastUpdate: Long


){
    companion object{
        const val TABLE_NAME = "type_price"
        const val TYPE_ID = "type_id"
        const val SYSTEM_ID = "system_id"
        const val REGION_ID = "region_id"
        const val SELL = "sell"
        const val BUY = "buy"
        const val UPDATE_TIME = "update_time"

    }
}
