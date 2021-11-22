package by.nepravsky.domain.usecase

import by.nepravsky.domain.entity.domain.ItemGroup
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.repository.ItemGroupRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.runFun
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetItemGroupsUseCase(private val itemGroupRepo: ItemGroupRepository){


    suspend fun get(settings: Settings):Result<List<ItemGroup>> =
        runFun { getAll(settings) }

    private suspend fun getAll(settings: Settings): List<ItemGroup> =
        withContext(IO){
            itemGroupRepo.getAll(settings)
        }
}