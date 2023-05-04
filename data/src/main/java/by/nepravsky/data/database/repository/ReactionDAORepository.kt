package by.nepravsky.data.database.repository

import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.entity.request.ItemRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.entity.request.ReactionRequest
import by.nepravsky.domain.entity.request.SearchReactionRequest

interface ReactionDAORepository {


    fun getAll(settings: Settings): List<ReactionFormula>
    fun getByIds(
        reactionRequest: ReactionRequest,
        settings: Settings
    ): ReactionFormula

    fun hasReactionFormula(
        items: List<ItemRequest>,
        settings: Settings
    ): List<ReactionFormula>

    fun getByName(
        searchReactionRequest: SearchReactionRequest,
        settings: Settings
    ): List<ReactionFormula>
}
