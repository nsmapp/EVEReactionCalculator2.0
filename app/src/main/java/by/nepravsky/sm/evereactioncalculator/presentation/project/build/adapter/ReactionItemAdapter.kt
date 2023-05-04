package by.nepravsky.sm.evereactioncalculator.presentation.project.build.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.nepravsky.domain.entity.base.ReactionItem
import by.nepravsky.domain.utils.getItemImageURL
import by.nepravsky.sm.evereactioncalculator.R
import by.nepravsky.sm.evereactioncalculator.databinding.ItemReactionItemBinding
import by.nepravsky.sm.evereactioncalculator.utils.*
import by.nepravsky.sm.evereactioncalculator.utils.views.slideToBottom
import by.nepravsky.sm.evereactioncalculator.utils.views.slideToTop
import coil.load

class ReactionItemAdapter: RecyclerView.Adapter<ReactionItemAdapter.ItemHolder>() {

    private val items = mutableListOf<ReactionItem>()
    private var isMaterials = true
    

    fun setItems(items: List<ReactionItem>, isMaterials: Boolean){
        this.isMaterials = isMaterials
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }



    inner class ItemHolder(val binding: ItemReactionItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(
            ItemReactionItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            val backGroundBorder = if (isMaterials) R.drawable.border_materials
                else R.drawable.border_products
            root.setBackgroundResource(backGroundBorder)
            val ctx = root.context
            
            ivItemIcon.load(getItemImageURL(item.typeId))
            tvName.text = "${item.quantity} x ${item.name}"
//            tvQuantity.text = "Quantity: ${item.quantity.toPC()}"
            tvVolume.text = "Volume: ${item.volume.toVolume()}"
            tvSell.text = "Sell: ${item.sell.toShortISK()}"
            tvBuy.text = "Buy: ${item.buy.toShortISK()}"
            tvTypeItem.text = if (isMaterials) ctx.getString(R.string.material)
                else ctx.getString(R.string.product)

            root.setOnClickListener {
                slideToTop(tvVolume, root)
                slideToTop(tvTypeItem, root)
                slideToBottom(tvBuy, root)
                slideToBottom(tvSell, root)
            }
        }
    }

    override fun getItemCount(): Int = items.size

}