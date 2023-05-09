package by.nepravsky.domain.usecase.group

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.repository.ItemGroupRepository
import by.nepravsky.domain.utils.runFunc

class UpdateGroupSelectionUseCase(
    private val itemGroupRepository: ItemGroupRepository
){
    suspend fun updateSelection(id: Int, isSelection: Boolean): Answer<Unit> =
        runFunc { itemGroupRepository.updateSelection(id, isSelection) }
}