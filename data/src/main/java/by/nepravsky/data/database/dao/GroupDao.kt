package by.nepravsky.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import by.nepravsky.data.database.entity.Group

@Dao
interface GroupDao {

    @Query("SELECT * FROM ${Group.TABLE_NAME} WHERE ${Group.ID} == :id")
    fun getById(id: Int): Group

    @Query("SELECT * FROM ${Group.TABLE_NAME} ORDER BY ${Group.ID} DESC")
    fun getAll(): List<Group>
}