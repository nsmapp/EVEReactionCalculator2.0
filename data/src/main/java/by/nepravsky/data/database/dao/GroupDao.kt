package by.nepravsky.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import by.nepravsky.data.database.entity.Group
import by.nepravsky.data.database.entity.Group.Companion.ID
import by.nepravsky.data.database.entity.Group.Companion.IS_SELECTED
import by.nepravsky.data.database.entity.Group.Companion.TABLE_NAME

@Dao
interface GroupDao {

    @Query("SELECT * FROM $TABLE_NAME WHERE $ID == :id")
    fun getById(id: Int): Group

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $ID DESC")
    fun getAll(): List<Group>

    @Query("UPDATE $TABLE_NAME SET $IS_SELECTED = :selection WHERE $ID == :id")
    fun updateSelection(id: Int, selection: Boolean)
}