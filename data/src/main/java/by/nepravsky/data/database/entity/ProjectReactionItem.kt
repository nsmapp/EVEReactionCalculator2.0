package by.nepravsky.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import by.nepravsky.data.database.entity.ProjectReactionItem.Companion.PROJECT_ID
import by.nepravsky.data.database.entity.ProjectReactionItem.Companion.REACTION_ID
import by.nepravsky.data.database.entity.ProjectReactionItem.Companion.TABLE_NAME

@Entity(indices = [Index(value = [PROJECT_ID, REACTION_ID], unique = true)], tableName = TABLE_NAME)
data class ProjectReactionItem(

    @ColumnInfo(name = PROJECT_ID)
    val projectId: Int,

    @ColumnInfo(name = REACTION_ID)
    val reactionId: Int,

    @ColumnInfo(name = RUN)
    val run: Int
) {
    companion object{
        const val TABLE_NAME = "project_items"
        const val ID = "id"
        const val PROJECT_ID = "parent_id"
        const val REACTION_ID = "reaction_id"
        const val RUN = "run"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    var id: Int = 0
}