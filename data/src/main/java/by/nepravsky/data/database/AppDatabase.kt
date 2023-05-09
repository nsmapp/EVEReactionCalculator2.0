package by.nepravsky.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.nepravsky.data.database.dao.*
import by.nepravsky.data.database.entity.*
import by.nepravsky.data.database.typeconverter.ReactionElementConverter

@Database(
    entities = [
        Reaction::class,
        Systems::class,
        Type::class,
        Categories::class,
        Group::class,
        Price::class,
        Setup::class,
        Language::class,
        TypePriceSource::class,
        ProjectReaction::class,
        ProjectReactionItem::class],
    version = 33
    )
@TypeConverters(ReactionElementConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun reactionDao(): ReactionDao
    abstract fun systemsDao(): SystemsDao
    abstract fun typeDao(): TypeDao
    abstract fun categoriesDao(): CategoriesDao
    abstract fun groupDao(): GroupDao
    abstract fun priceDao(): PriceDao
    abstract fun setupDao(): SetupDAO
    abstract fun languageDao(): LanguageDAO
    abstract fun typePriceSourceDao(): TypePriceSourceDao
    abstract fun reactionProjectDao(): ReactionProjectDao
    abstract fun reactionProjectItemDao(): ReactionProjectItemDao

}