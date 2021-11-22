package by.nepravsky.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.nepravsky.data.database.entity.Setup
import kotlinx.coroutines.flow.Flow

@Dao
interface SetupDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(setup: Setup): Long

    @Query("SELECT * FROM ${Setup.TABLE_NAME} WHERE ${Setup.ID} == 1 ")
    fun getSetup(): Flow<Setup>


}