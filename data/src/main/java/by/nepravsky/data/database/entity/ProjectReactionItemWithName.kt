package by.nepravsky.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.nepravsky.domain.entity.domain.Lang
@Entity
data class ProjectReactionItemWithName(
    @PrimaryKey
    @ColumnInfo(name = ID)  val id: Int,
    @ColumnInfo(name = PROJECT_ID) val projectId: Int,
    @ColumnInfo(name = REACTION_ID) val reactionId: Int,
    @ColumnInfo(name = RUN) val run: Int,

    @ColumnInfo(name = TypeWithPrice.DE)    val de: String,
    @ColumnInfo(name = TypeWithPrice.EN)    val en: String,
    @ColumnInfo(name = TypeWithPrice.FR)    val fr: String,
    @ColumnInfo(name = TypeWithPrice.JA)    val ja: String,
    @ColumnInfo(name = TypeWithPrice.RU)    val ru: String,
    @ColumnInfo(name = TypeWithPrice.ZH)    val zh: String


) {
    companion object {
        const val TABLE_NAME = "project_items"
        const val ID = "id"
        const val PROJECT_ID = "parent_id"
        const val REACTION_ID = "reaction_id"
        const val RUN = "run"

        const val DE: String = "de"
        const val EN: String = "en"
        const val FR: String = "fr"
        const val JA: String = "ja"
        const val RU: String = "ru"
        const val ZH: String = "zh"

    }

    fun getName(languageId: Int): String =
        when (languageId) {
            Lang.EN.id -> en
            Lang.FR.id -> fr
            Lang.DE.id -> de
            Lang.RU.id -> ru
            Lang.JA.id -> ja
            Lang.ZH.id -> zh
            else -> en

        }
}