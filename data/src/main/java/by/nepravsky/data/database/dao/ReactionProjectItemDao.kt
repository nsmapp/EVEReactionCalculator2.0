package by.nepravsky.data.database.dao

import androidx.room.*
import by.nepravsky.data.database.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReactionProjectItemDao {

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT " +
            "${ProjectReactionItem.TABLE_NAME}.*, " +
            "${Reaction.EN}, " +
            "${Reaction.FR}, " +
            "${Reaction.DE}, " +
            "${Reaction.RU}, " +
            "${Reaction.ZH}, " +
            "${Reaction.JA} " +
            "FROM ${ProjectReactionItem.TABLE_NAME} " +
            "JOIN ${Reaction.TABLE_NAME} " +
            "ON ${Reaction.TABLE_NAME}.${Reaction.ID} == ${ProjectReactionItem.TABLE_NAME}.${ProjectReactionItem.REACTION_ID} " +
            "WHERE ${ProjectReactionItem.PROJECT_ID} == :parentId "
    )
    fun getByParentIdFlow(parentId: Int): Flow<List<ProjectReactionItemWithName>>

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT " +
            "${ProjectReactionItem.TABLE_NAME}.*, " +
            "${Reaction.EN}, " +
            "${Reaction.FR}, " +
            "${Reaction.DE}, " +
            "${Reaction.RU}, " +
            "${Reaction.ZH}, " +
            "${Reaction.JA} " +
            "FROM ${ProjectReactionItem.TABLE_NAME} " +
            "JOIN ${Reaction.TABLE_NAME} " +
            "ON ${Reaction.TABLE_NAME}.${Reaction.ID} == ${ProjectReactionItem.TABLE_NAME}.${ProjectReactionItem.REACTION_ID} " +
            "WHERE ${ProjectReactionItem.PROJECT_ID} == :parentId "
    )
    fun getByParentId(parentId: Int): List<ProjectReactionItemWithName>

    @Query("DELETE FROM ${ProjectReactionItem.TABLE_NAME} " +
            "WHERE ${ProjectReactionItem.PROJECT_ID} == :parentId")
    fun deleteByProjectId(parentId: Int): Int

    @Query("DELETE FROM ${ProjectReactionItem.TABLE_NAME} " +
            "WHERE ${ProjectReactionItem.REACTION_ID} == :reactionId " +
            "AND ${ProjectReactionItem.PROJECT_ID} == :projectId ")
    fun deleteById(reactionId: Int, projectId: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(toData: ProjectReactionItem): Long
}