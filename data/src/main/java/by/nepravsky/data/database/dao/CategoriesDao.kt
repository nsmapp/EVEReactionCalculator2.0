package by.nepravsky.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import by.nepravsky.data.database.entity.Categories

@Dao
interface CategoriesDao {

    @Query("SELECT * FROM ${Categories.TABLE_NAME} WHERE ${Categories.ID} == :id")
    fun getById(id: Int): Categories
}