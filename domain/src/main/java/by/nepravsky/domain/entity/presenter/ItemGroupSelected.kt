package by.nepravsky.domain.entity.presenter

import by.nepravsky.domain.entity.domain.ItemGroup

data class ItemGroupSelected(
    val itemGroup: ItemGroup,
    var isSelected: Boolean
)