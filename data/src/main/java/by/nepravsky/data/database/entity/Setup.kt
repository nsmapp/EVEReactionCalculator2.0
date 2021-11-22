package by.nepravsky.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.nepravsky.data.database.entity.Setup.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class Setup(
    @ColumnInfo(name = LANG_ID )
    val languageId: Int,
    @ColumnInfo(name = SYSTEM_ID)
    val systemId: Int,
    @ColumnInfo(name = REGION_ID)
    val regionId: Int,
    @ColumnInfo(name = BPO_ME)
    val me: Int,
    @ColumnInfo(name = BPO_TE)
    val te: Int,
    @ColumnInfo(name = SUB_BPO_ME)
    val subME: Int,
    @ColumnInfo(name = SUB_BPO_TE)
    val subTE: Int,
    @ColumnInfo(name = PRICE_SOURCE)
    val priceUpdateSource: Int,

    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: Int = 1
){

    companion object{
        const val TABLE_NAME = "settings"
        const val ID = "id"
        const val LANG_ID = "lang_id"
        const val SYSTEM_ID = "system_id"
        const val REGION_ID = "region_id"
        const val BPO_ME = "bpo_me"
        const val BPO_TE = "bpo_te"
        const val SUB_BPO_ME = "sub_bpo_me"
        const val SUB_BPO_TE = "sub_bpo_te"
        const val PRICE_SOURCE = "price_update_source"
    }
}