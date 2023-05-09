package by.nepravsky.domain.usecase.group

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.entity.domain.ItemGroup
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.repository.ItemGroupRepository
import by.nepravsky.domain.utils.runFunc

class GetItemGroupsUseCase(private val itemGroupRepo: ItemGroupRepository) {


    suspend fun get(settings: Settings): Answer<List<ItemGroup>> =
        runFunc { itemGroupRepo.getAll(settings) }

}