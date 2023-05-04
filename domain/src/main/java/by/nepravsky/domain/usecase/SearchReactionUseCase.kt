package by.nepravsky.domain.usecase

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.entity.request.SearchReactionRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.repository.ReactionRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.runFun
import by.nepravsky.domain.utils.runFunc
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SearchReactionUseCase(
    private val reactionRepository: ReactionRepository
) {

    suspend fun get(
        request: SearchReactionRequest, settings: Settings
    ): Answer<List<ReactionFormula>> = runFunc { reactionRepository.getByName(request, settings) }


}