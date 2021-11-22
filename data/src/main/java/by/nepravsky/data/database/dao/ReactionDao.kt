package by.nepravsky.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import by.nepravsky.data.database.entity.*

@Dao
interface ReactionDao {

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT " +
                "${Reaction.TABLE_NAME}.*, " +
                "${Group.TABLE_NAME}.${Group.IS_FORMULA}, " +
                "${Group.TABLE_NAME}.${Group.CATEGORY} " +
            "FROM ${Reaction.TABLE_NAME} " +
            "JOIN ${Group.TABLE_NAME} " +
                "ON ${Group.TABLE_NAME}.${Group.ID} == ${Reaction.TABLE_NAME}.${Reaction.GROUP_ID} " +
            "ORDER BY ${Reaction.GROUP_ID}")
    fun getAll(): List<ReactionWithMEType>

    @Query("SELECT " +
            "${Reaction.TABLE_NAME}.*, " +
            "${Group.IS_FORMULA}, " +
            "${Group.CATEGORY} " +
            "FROM ${Reaction.TABLE_NAME} " +
            "JOIN ${Group.TABLE_NAME} " +
            "ON ${Group.TABLE_NAME}.${Group.ID} == ${Reaction.TABLE_NAME}.${Reaction.GROUP_ID} " +
            "WHERE ${Reaction.GROUP_ID} IN (:groupIds)  " +
            "ORDER BY ${Reaction.GROUP_ID}")
    fun getByGroup(groupIds: List<Int>): List<ReactionWithMEType>

    @Query("SELECT " +
                "${Reaction.TABLE_NAME}.*, " +
                "${Group.IS_FORMULA}, " +
                "${Group.CATEGORY} " +
            "FROM ${Reaction.TABLE_NAME} " +
            "JOIN ${Group.TABLE_NAME} " +
                "ON ${Group.TABLE_NAME}.${Group.ID} == ${Reaction.TABLE_NAME}.${Reaction.GROUP_ID} " +
            "WHERE reaction.id == :id")
    fun getById(id: Int): ReactionWithMEType

    @Query("SELECT " +
                "${Reaction.TABLE_NAME}.*, " +
                "${Group.IS_FORMULA}, " +
                "${Group.CATEGORY} " +
            "FROM ${Reaction.TABLE_NAME} " +
            "JOIN ${Group.TABLE_NAME} " +
                "ON ${Group.TABLE_NAME}.${Group.ID} == ${Reaction.TABLE_NAME}.${Reaction.GROUP_ID} " +
            "WHERE reaction.id IN (:ids)")
    fun getByIds(ids: List<Int>): List<ReactionWithMEType>
}