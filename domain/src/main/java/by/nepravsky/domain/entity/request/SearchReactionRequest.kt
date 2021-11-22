package by.nepravsky.domain.entity.request

import by.nepravsky.domain.entity.domain.ItemGroup

data class SearchReactionRequest(
    val name: String,
    val itemGroup: List<ItemGroup>
)