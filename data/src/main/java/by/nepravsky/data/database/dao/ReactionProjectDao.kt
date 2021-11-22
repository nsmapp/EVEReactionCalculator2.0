package by.nepravsky.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.nepravsky.data.database.entity.ProjectReaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ReactionProjectDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectReaction: ProjectReaction):Long

    @Query("SELECT * FROM ${ProjectReaction.TABLE_NAME} WHERE  ${ProjectReaction.ID} == :projectId ")
    fun getById(projectId: Int): ProjectReaction

    @Query("SELECT * FROM ${ProjectReaction.TABLE_NAME}")
    fun getAll(): Flow<List<ProjectReaction>>

    @Query("DELETE FROM ${ProjectReaction.TABLE_NAME} WHERE ${ProjectReaction.ID} == :projectId ")
    fun deleteById(projectId: Int): Int
}