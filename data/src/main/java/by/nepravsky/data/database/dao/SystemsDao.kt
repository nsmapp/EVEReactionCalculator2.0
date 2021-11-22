package by.nepravsky.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import by.nepravsky.data.database.entity.Systems

@Dao
interface SystemsDao {

    @Query("SELECT * FROM ${Systems.TABLE_NAME} WHERE ${Systems.ID} == :id")
    fun getById(id: Int):List<Systems>

    @Query("SELECT * FROM ${Systems.TABLE_NAME}")
    fun getAll(): List<Systems>

    @Query("SELECT ${Systems.ID} FROM ${Systems.TABLE_NAME}")
    fun getIds(): List<Int>
}