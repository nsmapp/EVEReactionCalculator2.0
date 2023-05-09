package by.nepravsky.domain.entity.domain


data class ItemGroup(
    val id: Int,
    val isFormula: Boolean,
    val isSelected: Boolean,
    val category: Int,
    val name: String,
    val iconId: Int
)