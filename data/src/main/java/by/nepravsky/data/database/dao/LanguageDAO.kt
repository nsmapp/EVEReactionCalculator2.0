package by.nepravsky.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import by.nepravsky.data.database.entity.Language

@Dao
interface LanguageDAO {

    @Query("SELECT * FROM ${Language.TABLE_NAME} ")
    fun getAll():List<Language>
}