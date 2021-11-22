package by.nepravsky.sm.evereactioncalculator.presentation.recipe.createitem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.nepravsky.sm.evereactioncalculator.databinding.ItemReactionBinding
import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.utils.getItemImageURL
import by.nepravsky.sm.evereactioncalculator.utils.listnersinterface.ItemClickListener
import by.nepravsky.sm.evereactioncalculator.utils.toTime
import coil.load

class ReactionListAdapter(
    private val itemClickListener: ItemClickListener<ReactionFormula>
) : RecyclerView.Adapter<ReactionListAdapter.ReactionHolder>() {

    private val items = mutableListOf<ReactionFormula>()


    fun setItems(reactions: List<ReactionFormula>) {
        items.clear()
        items.addAll(reactions)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReactionHolder =
        ReactionHolder(
            ItemReactionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ReactionHolder, position: Int) {
        val item = items[position]
        holder.binding.reactionName.text = item.name
        holder.binding.baseTime.text = item.baseTime.toTime()
        holder.binding.ivReactionIcon.load(getItemImageURL(item.id))
        holder.binding.tvUnitPerRun.text = "-"
        item.products.firstOrNull() { it.typeId == item.id }?.let {
            holder.binding.tvUnitPerRun.text = it.quantity.toString()
        }


        holder.binding.root.setOnClickListener {
            itemClickListener.onItemClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ReactionHolder(val binding: ItemReactionBinding) :
        RecyclerView.ViewHolder(binding.root)
}