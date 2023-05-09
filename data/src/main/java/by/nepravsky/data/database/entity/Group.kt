package by.nepravsky.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.nepravsky.data.database.entity.Group.Companion.TABLE_NAME
import by.nepravsky.domain.entity.domain.Lang

@Entity(tableName = TABLE_NAME)
data class Group(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: Int,

    @ColumnInfo(name = IS_FORMULA)
    val isFormula: Boolean,

    @ColumnInfo(name = IS_SELECTED)
    val isSelected: Boolean,

    @ColumnInfo(name = CATEGORY)
    val category: Int,

    @ColumnInfo(name = DE)
    val de: String,

    @ColumnInfo(name = EN)
    val en: String,

    @ColumnInfo(name = FR)
    val fr: String,

    @ColumnInfo(name = JA)
    val ja: String,

    @ColumnInfo(name = RU)
    val ru: String,

    @ColumnInfo(name = ZH)
    val zh: String
) {
    companion object{
        const val TABLE_NAME = "type_group"
        const val ID = "id"
        const val IS_FORMULA = "is_formula"
        const val IS_SELECTED = "is_selected"
        const val CATEGORY = "category"
        const val DE = "de"
        const val EN = "en"
        const val FR = "fr"
        const val JA = "ja"
        const val RU = "ru"
        const val ZH = "zh"

    }
    fun getName(languageId: Int): String =
        when(languageId){
            Lang.EN.id -> en
            Lang.FR.id -> fr
            Lang.DE.id -> de
            Lang.RU.id -> ru
            Lang.JA.id -> ja
            Lang.ZH.id -> zh
            else -> en
        }

}