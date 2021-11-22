package by.nepravsky.sm.evereactioncalculator.presentation.recipe.productline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import by.nepravsky.sm.evereactioncalculator.databinding.ProductLineFragmentBinding
import by.nepravsky.domain.entity.base.Project
import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.sm.evereactioncalculator.presentation.recipe.productline.adapter.ProductLineAdapter
import by.nepravsky.sm.evereactioncalculator.presentation.recipe.productline.adapter.ProjectClickContract
import by.nepravsky.sm.evereactioncalculator.utils.views.ItemTouchHelperCallback
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class ProductLineFragment : Fragment(), ProjectClickContract {

    private val viewModel: ProductLineViewModel by inject()
    private lateinit var binding: ProductLineFragmentBinding
    private val projectItemAdapter = ProductLineAdapter(this)
    private val itemTouchHelperCallback = ItemTouchHelperCallback(projectItemAdapter)
    private val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)

    private val args: ProductLineFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductLineFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.projectId = args.projectId

        viewModel.getSettings()
        binding.rvItems.adapter = projectItemAdapter
        itemTouchHelper.attachToRecyclerView(binding.rvItems)


        lifecycleScope.launchWhenCreated {

            viewModel.settings.onEach {
                viewModel.getProject()
                viewModel.getProjectItems()
            }.launchIn(lifecycleScope)

            viewModel.items.onEach {
                projectItemAdapter.setItems(it)
            }.launchIn(lifecycleScope)

            viewModel.project.onEach {
                it?.let {setProjectDescription(it)}
            }.launchIn(lifecycleScope)
        }

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    viewModel.saveNewProject(
                        binding.etName.text.toString(),
                        binding.etDescription.text.toString()
                    )
                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            })

        binding.fbAdd.setOnClickListener {
            val action = ProductLineFragmentDirections
                .actionProductLineFragmentToItemCreateFragment()
                .setProjectId(viewModel.projectId)
            findNavController().navigate(action)
        }
    }

    private fun setProjectDescription(project: Project){
        binding.etName.setText(project.name)
        binding.etDescription.setText(project.description)
    }

    override fun onItemClick(item: ProjectItem) {
        val action = ProductLineFragmentDirections
            .actionProductLineFragmentToItemCreateFragment()
            .setItemId(item.reactionId)
            .setItemName(item.name)
            .setItemRunCount(item.run)
            .setProjectId(viewModel.projectId)
            .setIsEditMode(true)
        findNavController().navigate(action)
    }

    override fun onDeleteItemClick(item: ProjectItem) {
        viewModel.deleteProjectItem(item)
    }

}