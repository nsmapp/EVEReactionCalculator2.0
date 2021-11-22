package by.nepravsky.domain.entity.domain

import by.nepravsky.domain.entity.base.ReactionItem

data class SubProduct(
    val baseMaterials: List<ReactionItem>,
    val complexMaterials: List<ReactionItem>,
)
