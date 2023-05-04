package by.nepravsky.sm.evereactioncalculator.presentation.project.projects.model

import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.entity.domain.ItemGroup

sealed class ProjectsState{

    data class UpdateGroup(val data: List<ItemGroup>): ProjectsState()
    data class UpdateFormulas(val data: List<ReactionFormula>): ProjectsState()
    data class ShowProgress(val isLoading: Boolean): ProjectsState()
    object Nothing: ProjectsState()
}
