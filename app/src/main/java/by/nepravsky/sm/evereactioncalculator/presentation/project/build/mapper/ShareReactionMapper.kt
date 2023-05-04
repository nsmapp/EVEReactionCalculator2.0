package by.nepravsky.sm.evereactioncalculator.presentation.project.build.mapper

import android.content.Context
import by.nepravsky.domain.entity.base.ReactionItem
import by.nepravsky.domain.entity.presenter.ReactionInfo
import by.nepravsky.sm.evereactioncalculator.R
import by.nepravsky.sm.evereactioncalculator.utils.TEXT_NEXT_ROW
import by.nepravsky.sm.evereactioncalculator.utils.toISK
import by.nepravsky.sm.evereactioncalculator.utils.toVolume

class ShareReactionMapper(private val context: Context) {


    fun createShareEveReaction(reaction: ReactionInfo?): String =
        createShareReaction(true, reaction)

    fun createShareSimpleReaction(reaction: ReactionInfo?): String =
        createShareReaction(false, reaction)

    private fun createShareReaction(
        isEveReaction: Boolean,
        reaction: ReactionInfo?,
    ): String {

        val sb = StringBuilder()

        reaction?.let { react ->

            sb.append(
                context.getString(
                    R.string.feature_build_reaction_mask_products,
                    react.allProductVolume.toVolume()
                )
            )
            sb.append(
                context.getString(
                    R.string.feature_build_reaction_mask_sell_buy,
                    react.allProductSell.toISK(),
                    react.allProductBuy.toISK()
                )
            )

            react.products.forEach { item ->
                sb.append(
                    if (isEveReaction) createEveItem(item) else createSimpleItem(item)
                )
            }

            sb.append(TEXT_NEXT_ROW)
            sb.append(
                context.getString(
                    R.string.feature_build_reaction_mask_materials,
                    react.allMaterialVolume.toVolume()
                )
            )
            sb.append(
                context.getString(
                    R.string.feature_build_reaction_mask_sell_buy,
                    react.allMaterialSell.toISK(),
                    react.allMaterialBuy.toISK()
                )
            )
            react.materials.forEach { item ->
                sb.append(
                    if (isEveReaction) createEveItem(item) else createSimpleItem(item)
                )
            }
        }

        return sb.toString()
    }

    private fun createSimpleItem(item: ReactionItem): String =
        context.getString(R.string.feature_build_reaction_mask_simple_item, item.quantity.toString(), item.name)

    private fun createEveItem(item: ReactionItem): String =
        context.getString(R.string.feature_build_reaction_mask_eve_item,
            item.quantity.toString(), item.typeId.toString(), item.name)
}