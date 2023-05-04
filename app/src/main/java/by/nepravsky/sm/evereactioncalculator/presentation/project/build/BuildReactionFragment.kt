package by.nepravsky.sm.evereactioncalculator.presentation.project.build

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import by.nepravsky.domain.entity.presenter.ProjectType
import by.nepravsky.domain.entity.presenter.ReactionInfo
import by.nepravsky.sm.evereactioncalculator.R
import by.nepravsky.sm.evereactioncalculator.databinding.BuildReactionFragmentBinding
import by.nepravsky.sm.evereactioncalculator.presentation.project.build.adapter.ReactionItemAdapter
import by.nepravsky.sm.evereactioncalculator.utils.*
import by.nepravsky.sm.evereactioncalculator.utils.views.slideToTop
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class BuildReactionFragment : Fragment() {


    private val viewModel: BuildReactionViewModel by inject()
    private lateinit var binding: BuildReactionFragmentBinding

    private val uiSettings: UISettings by inject()
    private val args: BuildReactionFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BuildReactionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.projectType = ProjectType.getById(args.projectTypeId)
        viewModel.reactionId = args.reactionId
        viewModel.loadSettings()
        binding.rvItems.setItemViewCacheSize(3)

        loadUiSettings()
        initFlows()
        initView()

    }

    private fun initFlows() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    renderState(state)
                }
            }
        }
    }

    private fun renderState(state: ViewState<ReactionInfo>) {
        when (state) {
            is ViewState.Loading -> binding.pbProgress.visibility = View.VISIBLE
            is ViewState.Success -> renderReaction(state.data)
            is ViewState.ShareReaction -> shareReaction(state.data)
            is ViewState.Error -> showError(state)
        }

    }

    private fun renderReaction(info: ReactionInfo) {
        binding.pbProgress.visibility = View.GONE

        val productAdapter = ReactionItemAdapter()
        val materialAdapter = ReactionItemAdapter()
        productAdapter.setItems(info.products, false)
        materialAdapter.setItems(info.materials, true)
        val mergeAdapter = ConcatAdapter(productAdapter, materialAdapter)

        with(binding) {
            rvItems.adapter = mergeAdapter

            tvProdVol.text = info.allProductVolume.toVolume()
            tvProdSell.text = info.allProductSell.toShortISK()
            tvProdBuy.text = info.allProductBuy.toShortISK()
            tvProdPriceDif.text = info.getProductPriceDif().toShortISK()

            tvMaterialVol.text = info.allMaterialVolume.toVolume()
            tvMaterialSell.text = info.allMaterialSell.toShortISK()
            tvMaterialBuy.text = info.allMaterialBuy.toShortISK()
            tvMaterialPriceDif.text = info.getMaterialPriceDif().toShortISK()

        }
    }

    private fun showError(state: ViewState.Error<ReactionInfo>) {
        binding.pbProgress.visibility = View.GONE
        showSnackBarSort(binding.root, getString(state.messageId))
    }

    private fun initView() {
        binding.apply {
            cbFullChain.setOnCheckedChangeListener { _, isChecked ->
                viewModel.isFullChain = isChecked
                viewModel.runReaction()
            }

            etRuns.doOnTextChanged { text, _, _, _ -> viewModel.changeRunCount(text) }
            etMe.doOnTextChanged { text, _, _, _ -> viewModel.changeME(text) }
            etSubMe.doOnTextChanged { text, _, _, _ -> viewModel.changeSubMe(text) }

            ivMinimize.setOnClickListener { changeBaseInfoVisible() }
            ivShare.setOnClickListener { showPopupMenu() }
        }
    }

    private fun showPopupMenu() {
        val menu = PopupMenu(requireContext(), binding.ivShare)
        menu.menu
            .add(getString(R.string.feature_build_reaction_simple_text))
            .setOnMenuItemClickListener {
                viewModel.shareReaction(true)
                false
            }
        menu.menu.add(getString(R.string.feature_build_reaction_for_eve_note))
            .setOnMenuItemClickListener {
                viewModel.shareReaction(false)
                false
            }
        menu.show()
    }

    private fun shareReaction(reaction: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, reaction)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

    }

    private fun loadUiSettings() {
        with(binding) {
            clBuildOptions.visibility = if (uiSettings.isShowRunME()) View.VISIBLE else View.GONE
            clBaseInfo.visibility = if (uiSettings.isShowReactionInfo()) View.VISIBLE else View.GONE
            ivMinimize.setBackgroundResource(getMinimizeResourceId(binding.clBaseInfo.isVisible))

            if (viewModel.projectType == ProjectType.Project)
                binding.tilRuns.visibility = View.GONE
        }
    }


    private fun changeBaseInfoVisible() {
        binding.apply {
            slideToTop(clBaseInfo, clTopMenu)
            binding.ivMinimize
                .setBackgroundResource(getMinimizeResourceId(clBaseInfo.isVisible))
        }
        uiSettings.setShowBaseInfo(binding.clBaseInfo.isVisible)

    }

    private fun getMinimizeResourceId(isViewVisible: Boolean) =
        if (isViewVisible) R.drawable.outline_remove_24
        else R.drawable.outline_maximize_24

}