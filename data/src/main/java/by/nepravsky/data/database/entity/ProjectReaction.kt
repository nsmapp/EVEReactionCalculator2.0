package by.nepravsky.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.nepravsky.data.database.entity.ProjectReaction.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ProjectReaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int,

    @ColumnInfo(name = NAME)
    val name: String,

    @ColumnInfo(name = DESCRIPTION)
    val description: String
) {
    companion object{
        const val TABLE_NAME = "projects"
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
    }
}