package by.nepravsky.data.repoimpl

import by.nepravsky.data.database.repository.GroupDAORepository
import by.nepravsky.domain.entity.domain.ItemGroup
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.repository.ItemGroupRepository

class ItemGroupRepoImpl(private val groupDAORepository: GroupDAORepository): ItemGroupRepository {

    override suspend fun getAll(settings: Settings): List<ItemGroup> =
        groupDAORepository.getAll(settings)
}