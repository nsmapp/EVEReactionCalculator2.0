package by.nepravsky.sm.evereactioncalculator.presentation.recipe.createitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.nepravsky.sm.evereactioncalculator.databinding.CreateItemFragmentBinding
import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.domain.entity.base.ReactionFormula
import by.nepravsky.domain.entity.domain.ItemGroup
import by.nepravsky.domain.utils.getItemImageURL
import by.nepravsky.sm.evereactioncalculator.utils.listnersinterface.ItemSelectedListener
import by.nepravsky.sm.evereactioncalculator.presentation.recipe.createitem.adapters.ItemGroupsAdapter
import by.nepravsky.sm.evereactioncalculator.presentation.recipe.createitem.adapters.ReactionListAdapter
import by.nepravsky.sm.evereactioncalculator.utils.TEXT_EMPTY
import by.nepravsky.sm.evereactioncalculator.utils.UISettings
import by.nepravsky.sm.evereactioncalculator.utils.events.Event
import by.nepravsky.sm.evereactioncalculator.utils.events.EventFinish
import by.nepravsky.sm.evereactioncalculator.utils.events.EventShowSnackBar
import by.nepravsky.sm.evereactioncalculator.utils.hideAndDisabled
import by.nepravsky.sm.evereactioncalculator.utils.listnersinterface.ItemClickListener
import by.nepravsky.sm.evereactioncalculator.utils.showSnackBarSort
import by.nepravsky.sm.evereactioncalculator.utils.views.slideToBottom
import coil.clear
import coil.dispose
import coil.load
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject


class ItemCreateFragment : Fragment(), ItemClickListener<ReactionFormula>,
    ItemSelectedListener<List<ItemGroup>> {

    private val uiSettings: UISettings by inject()
    private val viewModel: ItemCreateViewModel by inject()
    private lateinit var binding: CreateItemFragmentBinding

    private val reactionRAdapter = ReactionListAdapter(this)
    private val itemGroupAdapter = ItemGroupsAdapter(this)
    private val args: ItemCreateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateItemFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.isEditMode) initEditMode()
        else initCreateItemMode()

        viewModel.getSettings()

        initFlow()
        initListeners()
    }

    private fun initFlow() {
        lifecycleScope.launchWhenCreated {
            viewModel.settings
                .onEach { viewModel.getGroups(it) }
                .launchIn(lifecycleScope)

            viewModel.reactions.onEach { reactionRAdapter.setItems(it) }
                .launchIn(lifecycleScope)

            viewModel.group
                .onEach { itemGroupAdapter.setItems(it) }
                .launchIn(lifecycleScope)

            viewModel.projectItem.onEach {
                showProjectItem(it)
            }.launchIn(lifecycleScope)

            viewModel.eventList.onEach { launchEvent(it) }
                .launchIn(lifecycleScope)

            viewModel.progress
                .onEach { binding.pbProgress.visibility = if (it) View.VISIBLE else View.GONE }
                .launchIn(lifecycleScope)
        }
    }


    private fun initListeners() {
        binding.etReactionSearch.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.toString().isEmpty()) viewModel.searchReaction("")
                else viewModel.searchReaction(text.toString())
            } else viewModel.searchReaction("")
        }

        binding.etRuns.doOnTextChanged { text, _, _, _ ->
            viewModel.changeRuns(text)
        }

        binding.ivSettings.setOnClickListener {
            slideToBottom(binding.rvGroups, binding.root)
        }

        binding.ivAdd.setOnClickListener { viewModel.upRun() }

        binding.ivDelete.setOnClickListener { viewModel.downRun() }

        binding.ivSave.setOnClickListener { viewModel.saveProjectItem() }

    }

    override fun selectedItems(selectedItems: List<ItemGroup>) {
        viewModel.setSelectedItemGroups(selectedItems)
        viewModel.searchReaction(binding.etReactionSearch.text.toString())
    }

    override fun onItemClick(item: ReactionFormula) {
        viewModel.initProjectItem(item.id, 1, item.name, args.projectId)
    }

    private fun showProjectItem(item: ProjectItem?) {
        binding.apply {
            if (item != null) {
                tvReactionName.text = item.name
                etRuns.setText(item.run.toString())
                etRuns.setSelection(etRuns.length())
                ivReactionIcon.load(getItemImageURL(item.reactionId))
            } else {
                binding.tvReactionName.text = TEXT_EMPTY
                binding.ivReactionIcon.dispose()

            }
        }
    }

    private fun launchEvent(event: Event) {
        when (event) {
            is EventShowSnackBar -> showSnackBarSort(binding.root, event.message)
            is EventFinish -> requireActivity().onBackPressed()
            else -> {
            }
        }
    }

    private fun initCreateItemMode() {
        binding.rvReactions.adapter = reactionRAdapter
        binding.rvGroups.adapter = itemGroupAdapter
    }

    private fun initEditMode() {

        viewModel.initProjectItem(args.itemId, args.itemRunCount, args.itemName, args.projectId)

        binding.apply {
            tilReactionSearch.hideAndDisabled()
            ivSettings.hideAndDisabled()
            rvReactions.hideAndDisabled()
            rvGroups.hideAndDisabled()
        }
    }
}