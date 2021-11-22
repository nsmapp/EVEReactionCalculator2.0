package by.nepravsky.sm.evereactioncalculator.presentation.recipe.productline.adapter

import by.nepravsky.domain.entity.base.ProjectItem

interface ProjectClickContract {

    fun onItemClick(item: ProjectItem)
    fun onDeleteItemClick(item: ProjectItem)
}