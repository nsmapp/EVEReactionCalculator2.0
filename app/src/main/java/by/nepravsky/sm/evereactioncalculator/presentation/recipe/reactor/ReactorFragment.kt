package by.nepravsky.sm.evereactioncalculator.presentation.recipe.reactor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import by.nepravsky.sm.evereactioncalculator.databinding.ReactorFragmentBinding
import by.nepravsky.domain.entity.presenter.ProjectType
import by.nepravsky.sm.evereactioncalculator.presentation.recipe.reactor.adapter.ProjectClickContract
import by.nepravsky.sm.evereactioncalculator.presentation.recipe.reactor.adapter.ProjectsAdapter
import by.nepravsky.sm.evereactioncalculator.utils.views.ItemTouchHelperCallback
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class ReactorFragment : Fragment(), ProjectClickContract {


    private val viewModel: ReactorViewModel by inject()
    private lateinit var binding: ReactorFragmentBinding

    private val projectAdapter = ProjectsAdapter(this)
    private val itemTouchHelperCallback = ItemTouchHelperCallback(projectAdapter)
    private val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ReactorFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvProductLines.adapter = projectAdapter
        itemTouchHelper.attachToRecyclerView(binding.rvProductLines)

        binding.fbAdd.setOnClickListener{
            viewModel.createNewProject()
        }

        lifecycleScope.launchWhenCreated {

            viewModel.getProjects()

            viewModel.projects.onEach {
                projectAdapter.setItems(it)
            }.launchIn(lifecycleScope)

            viewModel.newProjectId.onEach {
                if (it != -1L) {
                    onEditItemClick(it.toInt())
                    viewModel.newProjectCreated()
                }
            }.launchIn(lifecycleScope)
        }
    }

    override fun onRunProject(projectId: Int) {
        val action = ReactorFragmentDirections.actionReactorFragmentToBuildReaction()
            .setProjectTypeId(ProjectType.REACTION_PROJECT.id)
            .setReactionId(projectId)
        findNavController().navigate(action)
    }

    override fun onDeleteItemClick(projectId: Int) {
        viewModel.deleteProject(projectId)
    }

    override fun onEditItemClick(projectId: Int) {
        val action = ReactorFragmentDirections.toProductLine()
            .setProjectId(projectId)
        findNavController().navigate(action)
    }

}