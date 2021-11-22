package by.nepravsky.sm.evereactioncalculator.presentation.recipe.productline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.nepravsky.sm.evereactioncalculator.databinding.ItemProjectItemBinding
import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.domain.utils.getItemImageURL
import by.nepravsky.sm.evereactioncalculator.utils.listnersinterface.ItemTouchListener
import coil.load


class ProductLineAdapter(
    private val clickContract: ProjectClickContract
    ): RecyclerView.Adapter<ProductLineAdapter.ProjectItemHolder>(), ItemTouchListener<Int> {

    private val items = mutableListOf<ProjectItem>()

    fun setItems(projectsItem: List<ProjectItem>){
        val diffUtils = DiffUtil.calculateDiff(ProjectLineDiffUtilCallback(this.items, projectsItem))
        items.clear()
        items.addAll(projectsItem)
        diffUtils.dispatchUpdatesTo(this)
    }

    inner class ProjectItemHolder(val binding: ItemProjectItemBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectItemHolder =
        ProjectItemHolder(
            ItemProjectItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ProjectItemHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            reactionName.text =  "${item.name}"
            reactionRuns.text =  "Runs: ${item.run}"
            ivReactionIcon.load(getItemImageURL(item.reactionId))

            root.setOnClickListener {
                clickContract.onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onSwipe(position: Int) {
        clickContract.onDeleteItemClick(items[position])
    }
}