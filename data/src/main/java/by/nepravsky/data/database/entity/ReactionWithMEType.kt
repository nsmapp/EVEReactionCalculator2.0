package by.nepravsky.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import by.nepravsky.data.database.typeconverter.ReactionElementConverter

@Entity
data class ReactionWithMEType(

    @PrimaryKey
    @ColumnInfo(name = ID)         val id: Int,
    @ColumnInfo(name = GROUP_ID)   val groupId: Int,
    @ColumnInfo(name = BASE_TIME)  val baseTime: Int,
    @ColumnInfo(name = RUN_LIMIT)  val runLimit: Int,

    @ColumnInfo(name = MATERIALS)
    @TypeConverters(ReactionElementConverter::class) val materials: List<ReactionElement>,
    @ColumnInfo(name = PRODUCTS)
    @TypeConverters(ReactionElementConverter::class)  val products: List<ReactionElement>,

    @ColumnInfo(name = DE)         val de: String,
    @ColumnInfo(name = EN)         val en: String,
    @ColumnInfo(name = FR)         val fr: String,
    @ColumnInfo(name = JA)         val ja: String,
    @ColumnInfo(name = RU)         val ru: String,
    @ColumnInfo(name = ZH)         val zh: String,

    @ColumnInfo(name = IS_FORMULA) val isFormula: Boolean,
    @ColumnInfo(name = CATEGORY) val categoryId: Boolean
){
    companion object{
        const val TABLE_NAME = "reaction"
        const val ID: String = "id"
        const val GROUP_ID: String = "group_id"
        const val BASE_TIME: String = "base_time"
        const val RUN_LIMIT: String = "run_limit"
        const val MATERIALS: String = "materials"
        const val PRODUCTS: String = "products"
        const val DE: String = "de"
        const val EN: String = "en"
        const val FR: String = "fr"
        const val JA: String = "ja"
        const val RU: String = "ru"
        const val ZH: String = "zh"
        const val IS_FORMULA = "is_formula"
        const val CATEGORY = "category"
    }

    fun getNameById(languageId: Int): String =
        when(languageId){
            12 -> en
            13 -> fr
            14 -> de
            15 -> ru
            16 -> ja
            17 -> zh
            else -> en
        }
}