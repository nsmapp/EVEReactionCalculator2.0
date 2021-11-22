package by.nepravsky.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import by.nepravsky.data.database.entity.TypePriceSource

@Dao
interface TypePriceSourceDao {

    @Query("SELECT * FROM ${TypePriceSource.TABLE_NAME}")
    fun getAll(): List<TypePriceSource>
}