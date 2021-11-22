package by.nepravsky.domain.entity.base

data class ReactionFormula(
    val id: Int,
    val groupId: Int,
    val baseTime: Int,
    val runLimit: Int,
    val materials: List<ReactionFormulaElement>,
    val products: List<ReactionFormulaElement>,
    val name: String,
    val isFormula: Boolean
)