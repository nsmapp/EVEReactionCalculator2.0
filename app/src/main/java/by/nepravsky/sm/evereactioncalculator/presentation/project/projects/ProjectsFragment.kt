package by.nepravsky.sm.evereactioncalculator.presentation.project.projects

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.nepravsky.sm.evereactioncalculator.databinding.ProjectsFragmentBinding
import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.entity.domain.ItemGroup
import by.nepravsky.domain.entity.presenter.ProjectType
import by.nepravsky.sm.evereactioncalculator.presentation.project.projects.adapters.ItemGroupsAdapter
import by.nepravsky.sm.evereactioncalculator.presentation.project.projects.adapters.ReactionAdapter
import by.nepravsky.sm.evereactioncalculator.utils.UISettings
import by.nepravsky.sm.evereactioncalculator.utils.listnersinterface.ItemClickListener
import by.nepravsky.sm.evereactioncalculator.utils.listnersinterface.ItemSelectedListener
import by.nepravsky.sm.evereactioncalculator.utils.views.slideToBottom
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class ProjectsFragment : Fragment(), ItemClickListener<ReactionFormula>,
    ItemSelectedListener<List<ItemGroup>> {

    private val uiSettings: UISettings by inject()
    private val viewModel: ProjectsViewModel by inject()
    private lateinit var binding: ProjectsFragmentBinding
    private val reactionRAdapter = ReactionAdapter(this)
    private val itemGroupAdapter = ItemGroupsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProjectsFragmentBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSettings()

        initInterface()
        initFlows()
        initListner()
    }

    private fun initInterface() {

        binding.rvReactions.adapter = reactionRAdapter
        binding.rvItemGroups.adapter = itemGroupAdapter
    }

    private fun initFlows() {
        lifecycleScope.launchWhenCreated {
            viewModel.settings
                .onEach {
                    if (viewModel.group.value.isEmpty()) viewModel.getGroups(it)
                }
                .launchIn(lifecycleScope)

            viewModel.reactions
                .onEach { reactionRAdapter.setItems(it) }
                .launchIn(lifecycleScope)

            viewModel.group
                .onEach { if (itemGroupAdapter.itemCount == 0) itemGroupAdapter.setItems(it) }
                .launchIn(lifecycleScope)

            viewModel.progress
                .onEach { binding.pbProgress.visibility = if (it) View.VISIBLE else View.GONE }
                .launchIn(lifecycleScope)
        }
    }

    private fun initListner() {
        binding.etReactionSearch.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.toString().isEmpty()) viewModel.searchReaction("")
                else viewModel.searchReaction(text.toString())
            } else viewModel.searchReaction("")
        }

        binding.ivSettings.setOnClickListener {
            slideToBottom(binding.rvItemGroups, binding.root)
        }

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.rvItemGroups.isVisible) {
                        slideToBottom(binding.rvItemGroups, binding.root)
                        return
                    }

                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            })
    }

    override fun selectedItems(selectedItems: List<ItemGroup>) {
        viewModel.setSelectedItemGroups(selectedItems)
        viewModel.searchReaction(binding.etReactionSearch.text.toString())
    }

    override fun onItemClick(item: ReactionFormula) {
        val action = ProjectsFragmentDirections.toBuildReaction()
            .setReactionId(item.id)
            .setProjectTypeId(ProjectType.REACTION_SINGLE.id)
        findNavController().navigate(action)
    }


}