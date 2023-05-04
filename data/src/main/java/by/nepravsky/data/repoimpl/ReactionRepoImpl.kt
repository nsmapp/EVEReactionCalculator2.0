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

    override fun getAll(settings: Settings): List<ReactionFormula> =
        reactionDAORepository.getAll(settings)

    override fun getByName(
        searchReactionRequest: SearchReactionRequest,
        settings: Settings
    ): List<ReactionFormula> = reactionDAORepository.getByName(searchReactionRequest, settings)

    override fun get(
        reactionRequest: ReactionRequest,
        settings: Settings
    ): ReactionFormula =  reactionDAORepository.getByIds(reactionRequest, settings)

    override fun hasReactionFormula(
        items: List<ItemRequest>,
        settings: Settings
    ): List<ReactionFormula> = reactionDAORepository.hasReactionFormula(items, settings)

}