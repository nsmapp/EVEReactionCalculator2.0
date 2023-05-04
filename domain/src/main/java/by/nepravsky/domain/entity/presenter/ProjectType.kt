package by.nepravsky.domain.entity.presenter

sealed class ProjectType(val id: Long){

    object Single: ProjectType(1)
    object Project: ProjectType(2)

    companion object{
        fun getById(id: Long): ProjectType = when(id){
            Single.id -> Single
            Project.id ->Project
            else -> throw IllegalArgumentException("unknown ProjectType id: $id")
        }
    }
}
