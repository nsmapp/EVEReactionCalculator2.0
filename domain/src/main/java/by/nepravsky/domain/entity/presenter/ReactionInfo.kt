package by.nepravsky.domain.entity.presenter

import by.nepravsky.domain.entity.base.ReactionItem

data class ReactionInfo (

    val products: List<ReactionItem>,
    val materials: List<ReactionItem>,

    val allMaterialSell: Double,
    val allMaterialBuy: Double,
    val allMaterialVolume: Double,
    val allMaterialsQnt: Int,

    val allProductSell: Double,
    val allProductBuy: Double,
    val allProductVolume: Double,
    val allProductQnt: Int,
) {
    fun getMaterialPriceDif() = allMaterialSell - allMaterialBuy
    fun getProductPriceDif() = allProductSell - allProductBuy
}