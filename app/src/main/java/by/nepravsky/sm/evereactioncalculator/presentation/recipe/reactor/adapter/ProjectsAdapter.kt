package by.nepravsky.sm.evereactioncalculator.presentation.recipe.reactor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.nepravsky.sm.evereactioncalculator.databinding.ItemProjectBinding
import by.nepravsky.domain.entity.base.Project
import by.nepravsky.sm.evereactioncalculator.utils.listnersinterface.ItemTouchListener


class ProjectsAdapter(
    private val projectClickContract: ProjectClickContract
): RecyclerView.Adapter<ProjectsAdapter.ProjectHolder>(), ItemTouchListener<Int> {

    private val items = mutableListOf<Project>()

    fun setItems(projects: List<Project>){
        val diffResult = DiffUtil.calculateDiff(ProjectDiffUtilCallback(this.items, projects))
        items.clear()
        items.addAll(projects)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ProjectHolder(val binding: ItemProjectBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder =
        ProjectHolder(
            ItemProjectBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            tvProjectName.text = item.name
            tvDescription.text = item.description

            ivEdit.setOnClickListener {
                projectClickContract.onRunProject(item.id)
            }
            root.setOnClickListener {
                projectClickContract.onEditItemClick(item.id)
            }
            root.setOnLongClickListener {
                projectClickContract.onRunProject(item.id)
                true
            }
        }

    }

    override fun getItemCount(): Int = items.size

    override fun onSwipe(position: Int) {
        projectClickContract.onDeleteItemClick(items[position].id)
    }

}