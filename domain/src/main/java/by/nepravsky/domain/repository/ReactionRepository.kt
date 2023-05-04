package by.nepravsky.domain.repository

import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.entity.request.ItemRequest
import by.nepravsky.domain.entity.request.ReactionRequest
import by.nepravsky.domain.entity.request.SearchReactionRequest
import by.nepravsky.domain.entity.request.Settings

interface ReactionRepository {

    fun getAll(settings: Settings): List<ReactionFormula>

    fun getByName(
        searchReactionRequest: SearchReactionRequest,
        settings: Settings
    ): List<ReactionFormula>

   fun get(
        reactionRequest: ReactionRequest,
        settings: Settings
    ): ReactionFormula

    fun hasReactionFormula(
        items: List<ItemRequest>,
        settings: Settings
    ): List<ReactionFormula>
}
