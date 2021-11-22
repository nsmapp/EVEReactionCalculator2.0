package by.nepravsky.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import by.nepravsky.data.database.entity.Price
import by.nepravsky.data.database.entity.Type
import by.nepravsky.data.database.entity.TypeWithPrice

@Dao
interface TypeDao {

    @Query("SELECT * FROM ${Type.TABLE_NAME} WHERE ${Type.ID} == :id")
    fun getById(id: Int): Type

    @Query("SELECT COUNT(*) FROM ${Type.TABLE_NAME}")
    fun getAll(): Int

    @Query("SELECT ${Type.ID} FROM ${Type.TABLE_NAME}")
    fun getIds(): List<Int>

    @RewriteQueriesToDropUnusedColumns
    @Query(
        "SELECT * FROM ${Type.TABLE_NAME} " +
                "INNER JOIN ${Price.TABLE_NAME} ON ${Price.TYPE_ID} == ${Type.ID} " +
                "WHERE ${Price.SYSTEM_ID} == :solarSystemId AND ${Type.ID} == :typeId"
    )
    fun get(typeId: Int, solarSystemId: Int): TypeWithPrice
}