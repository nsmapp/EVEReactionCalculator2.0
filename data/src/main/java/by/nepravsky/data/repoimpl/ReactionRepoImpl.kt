package by.nepravsky.data.repoimpl

import by.nepravsky.data.database.repository.ReactionDAORepository
import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.entity.request.ItemRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.entity.request.ReactionRequest
import by.nepravsky.domain.entity.request.SearchReactionRequest
import by.nepravsky.domain.repository.ReactionRepository

class ReactionRepoImpl(
    private val reactionDAORepository: ReactionDAORepository
):ReactionRepository {

    override suspend fun getAll(settings: Settings): List<ReactionFormula> =
        reactionDAORepository.getAll(settings)

    override suspend fun getByName(
        searchReactionRequest: SearchReactionRequest,
        settings: Settings
    ): List<ReactionFormula> = reactionDAORepository.getByName(searchReactionRequest, settings)

    override suspend fun get(
        reactionRequest: ReactionRequest,
        settings: Settings
    ): ReactionFormula =  reactionDAORepository.getByIds(reactionRequest, settings)

    override suspend fun hasReactionFormula(
        items: List<ItemRequest>,
        settings: Settings
    ): List<ReactionFormula> = reactionDAORepository.hasReactionFormula(items, settings)

}