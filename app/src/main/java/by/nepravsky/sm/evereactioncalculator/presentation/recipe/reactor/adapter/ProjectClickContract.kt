package by.nepravsky.sm.evereactioncalculator.presentation.recipe.reactor.adapter

interface ProjectClickContract {

    fun onRunProject(projectId: Int)
    fun onDeleteItemClick(projectId: Int)
    fun onEditItemClick(projectId: Int)
}