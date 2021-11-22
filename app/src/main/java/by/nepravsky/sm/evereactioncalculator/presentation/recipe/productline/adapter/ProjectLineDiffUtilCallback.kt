package by.nepravsky.sm.evereactioncalculator.presentation.recipe.productline.adapter

import androidx.recyclerview.widget.DiffUtil
import by.nepravsky.domain.entity.base.ProjectItem

class ProjectLineDiffUtilCallback(
    private val olds: List<ProjectItem>,
    private val news: List<ProjectItem>
    ): DiffUtil.Callback() {


    override fun getOldListSize(): Int = olds.size

    override fun getNewListSize(): Int = news.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        olds[oldItemPosition].projectId == news[newItemPosition].projectId &&
                olds[oldItemPosition].reactionId == news[newItemPosition].reactionId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        olds[oldItemPosition].name == news[newItemPosition].name &&
                olds[oldItemPosition].run == news[newItemPosition].run &&
                olds[oldItemPosition].reactionId == news[newItemPosition].reactionId

}