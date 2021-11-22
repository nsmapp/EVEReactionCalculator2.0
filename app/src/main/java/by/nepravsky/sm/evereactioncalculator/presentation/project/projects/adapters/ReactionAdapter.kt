package by.nepravsky.sm.evereactioncalculator.presentation.project.projects.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.nepravsky.sm.evereactioncalculator.databinding.ItemReactionBinding
import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.utils.getItemImageURL
import by.nepravsky.sm.evereactioncalculator.utils.listnersinterface.ItemClickListener
import by.nepravsky.sm.evereactioncalculator.utils.toTime
import coil.load

class ReactionAdapter(
    private val itemClickListener: ItemClickListener<ReactionFormula>
): RecyclerView.Adapter<ReactionAdapter.ReactionHolder>() {

    private val items = mutableListOf<ReactionFormula>()


    fun setItems(reactions: List<ReactionFormula>){
        val diffUtils = DiffUtil.calculateDiff(ReactionDiffUtilCallback(this.items, reactions))
        items.clear()
        items.addAll(reactions)
        diffUtils.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReactionHolder =
        ReactionHolder(
            ItemReactionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))

    override fun onBindViewHolder(holder: ReactionHolder, position: Int) {
        val item = items[position]
        holder.binding.reactionName.text = item.name
        holder.binding.baseTime.text = item.baseTime.toTime()
        holder.binding.ivReactionIcon.load(getItemImageURL(item.id))
        holder.binding.root.setOnClickListener {
            itemClickListener.onItemClick(item)
        }
        holder.binding.tvUnitPerRun.text = "-"
        item.products.firstOrNull() { it.typeId == item.id }?.let {
            holder.binding.tvUnitPerRun.text = it.quantity.toString()
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ReactionHolder(val binding: ItemReactionBinding): RecyclerView.ViewHolder(binding.root)

    inner class ReactionDiffUtilCallback(
        private val olds: List<ReactionFormula>,
        private val news: List<ReactionFormula>
    ): DiffUtil.Callback() {


        override fun getOldListSize(): Int = olds.size

        override fun getNewListSize(): Int = news.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            olds[oldItemPosition].id == news[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            olds[oldItemPosition].name == news[newItemPosition].name

    }
}