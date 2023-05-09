package by.nepravsky.data.database.repository

import by.nepravsky.data.database.AppDatabase
import by.nepravsky.data.database.entity.Group
import by.nepravsky.domain.entity.domain.ItemGroup
import by.nepravsky.domain.entity.request.Settings

class GroupDAORepoImpl(private val appDatabase: AppDatabase) : GroupDAORepository {

    override fun getAll(settings: Settings): List<ItemGroup> =
        appDatabase.groupDao().getAll()
            .map {
                val reaction = appDatabase.reactionDao().getByGroup(it.id)
                toDomain(it, settings, reaction.id)
            }

    override fun updateSelection(id: Int, isSelection: Boolean) {
        appDatabase.groupDao().updateSelection(id, isSelection)
    }


    private fun toDomain(group: Group, settings: Settings, iconId: Int): ItemGroup =
        ItemGroup(
            group.id,
            group.isFormula,
            group.isSelected,
            group.category,
            group.getName(settings.languageId),
            iconId
        )

}