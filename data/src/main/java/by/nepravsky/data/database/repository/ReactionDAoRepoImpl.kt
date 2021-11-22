package by.nepravsky.data.database.repository

import by.nepravsky.data.database.AppDatabase
import by.nepravsky.data.database.entity.ReactionElement
import by.nepravsky.data.database.entity.ReactionWithMEType
import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.entity.base.ReactionFormulaElement
import by.nepravsky.domain.entity.domain.Lang
import by.nepravsky.domain.entity.request.ItemRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.entity.request.ReactionRequest
import by.nepravsky.domain.entity.request.SearchReactionRequest

class ReactionDAoRepoImpl(
    private val appDatabase: AppDatabase
): ReactionDAORepository {

    override suspend fun getAll(settings: Settings): List<ReactionFormula> =
        appDatabase.reactionDao().getAll()
            .map{toDomain(it,settings.languageId)}

    override suspend fun getByIds(
        reactionRequest: ReactionRequest,
        settings: Settings
    ): ReactionFormula = toDomain(
        appDatabase.reactionDao().getById(reactionRequest.reactionId),
        settings.languageId
    )

    override suspend fun hasReactionFormula(
        items: List<ItemRequest>,
        settings: Settings
    ): List<ReactionFormula> = appDatabase.reactionDao()
        .getByIds(items.map { it.itemId })
        .map { toDomain(it, settings.languageId) }


    override suspend fun getByName(
        searchReactionRequest: SearchReactionRequest,
        settings: Settings
    ): List<ReactionFormula> {
        val reactions = appDatabase.reactionDao()
            .getByGroup(searchReactionRequest.itemGroup.map { it.id })
        val filters = filterByName(reactions, searchReactionRequest.name, settings.languageId)
        return filters.map { toDomain(it, settings.languageId) }
    }


    private fun toDomain(reaction: ReactionWithMEType, languageId: Int): ReactionFormula =
        ReactionFormula(
            reaction.id,
            reaction.groupId,
            reaction.baseTime,
            reaction.runLimit,
            reaction.materials.map { toDomain(it) },
            reaction.products.map{ toDomain(it) },
            reaction.getNameById(languageId),
            reaction.isFormula
        )

    private fun toDomain(element: ReactionElement): ReactionFormulaElement =
        ReactionFormulaElement(
            element.quantity,
            element.typeID
        )

    private fun filterByName(
        reactions: List<ReactionWithMEType>,
        name: String,
        languageId: Int
    ): List<ReactionWithMEType> =
        when(languageId){
            Lang.EN.id -> reactions.filter { it.en.contains(name, true) }
            Lang.FR.id -> reactions.filter { it.fr.contains(name, true) }
            Lang.DE.id -> reactions.filter { it.de.contains(name, true) }
            Lang.RU.id -> reactions.filter { it.ru.contains(name, true) }
            Lang.JA.id -> reactions.filter { it.ja.contains(name, true) }
            Lang.ZH.id -> reactions.filter { it.zh.contains(name, true) }
            else -> reactions.filter { it.en.contains(name, true) }
        }
}