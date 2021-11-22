package by.nepravsky.sm.evereactioncalculator.presentation.recipe.reactor.adapter

import androidx.recyclerview.widget.DiffUtil
import by.nepravsky.domain.entity.base.Project

class ProjectDiffUtilCallback(
    private val olds: List<Project>,
    private val news: List<Project>
    ): DiffUtil.Callback() {


    override fun getOldListSize(): Int = olds.size

    override fun getNewListSize(): Int = news.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        olds[oldItemPosition].id == news[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        olds[oldItemPosition].name == news[newItemPosition].name &&
                olds[oldItemPosition].description == news[newItemPosition].description
}