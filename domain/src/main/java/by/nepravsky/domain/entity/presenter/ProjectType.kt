package by.nepravsky.domain.entity.presenter

sealed class ProjectType(val id: Long){

    object REACTION_SINGLE: ProjectType(1)
    object REACTION_PROJECT: ProjectType(2)
}
