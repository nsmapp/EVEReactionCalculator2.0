package by.nepravsky.sm.evereactioncalculator.presentation.project.projects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.entity.domain.ItemGroup
import by.nepravsky.domain.entity.presenter.ProjectType
import by.nepravsky.sm.evereactioncalculator.databinding.ProjectsFragmentBinding
import by.nepravsky.sm.evereactioncalculator.presentation.project.projects.adapters.ItemGroupsAdapter
import by.nepravsky.sm.evereactioncalculator.presentation.project.projects.adapters.ReactionAdapter
import by.nepravsky.sm.evereactioncalculator.presentation.project.projects.model.ProjectsState
import by.nepravsky.sm.evereactioncalculator.presentation.recipe.createitem.adapters.ItemGroupListener
import by.nepravsky.sm.evereactioncalculator.utils.TEXT_EMPTY
import by.nepravsky.sm.evereactioncalculator.utils.listnersinterface.ItemClickListener
import by.nepravsky.sm.evereactioncalculator.utils.views.slideToBottom
import by.nepravsky.sm.evereactioncalculator.utils.views.slideToTop
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ProjectsFragment : Fragment(), ItemClickListener<ReactionFormula>,
     ItemGroupListener{

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
        initListener()
    }

    private fun initInterface() {

        binding.rvReactions.adapter = reactionRAdapter
        binding.rvItemGroups.adapter = itemGroupAdapter
    }

    private fun initFlows() {

        lifecycleScope.launch {
            viewModel.state.collectLatest { state -> renderUI(state) }
        }
    }

    private fun initListener() {
        binding.etReactionSearch.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.toString().isEmpty()) viewModel.searchReaction(TEXT_EMPTY)
                else viewModel.searchReaction(text.toString())
            } else viewModel.searchReaction(TEXT_EMPTY)
        }

        binding.ivSettings.setOnClickListener {
            slideToTop(binding.rvItemGroups, binding.root)
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

    private fun renderUI(state: ProjectsState) {
        when (state) {
            is ProjectsState.ShowProgress -> showProgress(state.isLoading)
            is ProjectsState.UpdateFormulas -> showFormulas(state)
            is ProjectsState.UpdateGroup -> showGroups(state)
            is ProjectsState.Nothing -> doNothing()
        }
    }

    private fun showProgress(loading: Boolean) {
        binding.pbProgress.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun doNothing() {
        viewModel.stopProgress()
    }

    private fun showGroups(state: ProjectsState.UpdateGroup) {
        itemGroupAdapter.setItems(state.data)
    }

    private fun showFormulas(state: ProjectsState.UpdateFormulas) {
        reactionRAdapter.setItems(state.data)
    }

    override fun onItemClick(item: ReactionFormula) {
        val action = ProjectsFragmentDirections.toBuildReaction()
            .setReactionId(item.id)
            .setProjectTypeId(ProjectType.Single.id)
        findNavController().navigate(action)
    }

    override fun updateGroupSelection(id: Int, isSelection: Boolean) {
        viewModel.updateGroupSelection(id, isSelection)
    }

}