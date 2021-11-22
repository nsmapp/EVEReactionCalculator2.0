package by.nepravsky.domain.repository

import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.entity.request.ItemRequest
import by.nepravsky.domain.entity.request.ReactionRequest
import by.nepravsky.domain.entity.request.SearchReactionRequest
import by.nepravsky.domain.entity.request.Settings

interface ReactionRepository {

    suspend fun getAll(settings: Settings): List<ReactionFormula>

    suspend fun getByName(
        searchReactionRequest: SearchReactionRequest,
        settings: Settings
    ): List<ReactionFormula>

    suspend fun get(
        reactionRequest: ReactionRequest,
        settings: Settings
    ): ReactionFormula

    suspend fun hasReactionFormula(
        items: List<ItemRequest>,
        settings: Settings
    ): List<ReactionFormula>
}
