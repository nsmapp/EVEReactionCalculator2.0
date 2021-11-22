package by.nepravsky.sm.evereactioncalculator.presentation.project.build

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import by.nepravsky.sm.evereactioncalculator.R
import by.nepravsky.sm.evereactioncalculator.databinding.BuildReactionFragmentBinding
import by.nepravsky.domain.entity.presenter.ProjectType
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.sm.evereactioncalculator.presentation.project.build.adapter.ReactionItemAdapter
import by.nepravsky.sm.evereactioncalculator.utils.*
import by.nepravsky.sm.evereactioncalculator.utils.events.Event
import by.nepravsky.sm.evereactioncalculator.utils.events.EventShowSnackBar
import by.nepravsky.sm.evereactioncalculator.utils.views.slideToEnd
import by.nepravsky.sm.evereactioncalculator.utils.views.slideToTop
import kotlinx.coroutines.flow.*
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
        viewModel.showProgress()
        binding = BuildReactionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.projectTypeId = args.projectTypeId
        viewModel.reactionId = args.reactionId
        viewModel.loadSettings()
        binding.rvItems.setItemViewCacheSize(3)

        initInterface()
        initFlows()
        initView()

    }

    private fun initFlows() {
        lifecycleScope.launchWhenStarted {
            viewModel.progress
                .onEach { binding.pbProgress.visibility = if (it) View.VISIBLE else View.GONE }
                .launchIn(lifecycleScope)

            viewModel.reactionInfo
                .onEach {
                    viewModel.stopProgress()
                    it?.let {
                        val productAdapter = ReactionItemAdapter()
                        val materialAdapter = ReactionItemAdapter()
                        productAdapter.setItems(it.products, false)
                        materialAdapter.setItems(it.materials, true)
                        val mergeAdapter = ConcatAdapter(productAdapter, materialAdapter)
                        binding.rvItems.adapter = mergeAdapter
                        binding.apply {
                            tvProdVol.text = it.allProductVolume.toVolume()
                            tvProdSell.text = it.allProductSell.toISK()
                            tvProdBuy.text = it.allProductBuy.toISK()
                            tvProdPriceDif.text = it.getProductPriceDif().toISK()

                            tvMaterialVol.text = it.allMaterialVolume.toVolume()
                            tvMaterialSell.text = it.allMaterialSell.toISK()
                            tvMaterialBuy.text = it.allMaterialBuy.toISK()
                            tvMaterialPriceDif.text = it.getMaterialPriceDif().toISK()
                        }

                    }
                }
                .launchIn(lifecycleScope)

            viewModel.eventList.onEach { launchEvent(it) }
                .launchIn(lifecycleScope)
        }
    }

    private fun launchEvent(event: Event) {
        when (event) {
            is EventShowSnackBar -> showSnackBarSort(binding.root, event.message)
            else -> {
            }
        }

    }

    private fun showBuildSettings(settings: Settings) {
        binding.apply {
            etMe.setText(settings.me.toString())
            etSubMe.setText(settings.subME)
        }
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

            ivBaseInfo.setOnClickListener { changeBaseInfoVisible() }
            ivReactionSettings.setOnClickListener { changeBuildOptionsVisible() }
        }
    }

    private fun initInterface() {
        binding.clBuildOptions.visibility =
            if (uiSettings.isShowRunME()) View.VISIBLE
            else View.GONE

        binding.clBaseInfo.visibility =
            if (uiSettings.isShowReactionInfo()) View.VISIBLE
            else View.GONE

        binding.ivReactionSettings
            .setBackgroundResource(getArrowResourceId(binding.clBuildOptions.isVisible))

        binding.ivBaseInfo
            .setBackgroundResource(getArrowResourceId(binding.clBaseInfo.isVisible))

        if (viewModel.projectTypeId == ProjectType.REACTION_PROJECT.id)
            binding.tilRuns.visibility = View.GONE
    }


    private fun changeBaseInfoVisible() {
        binding.apply {
            slideToTop(clBaseInfo, root)
            binding.ivBaseInfo
                .setBackgroundResource(getArrowResourceId(clBaseInfo.isVisible))
        }
        uiSettings.setShowBaseInfo(binding.clBaseInfo.isVisible)

    }

    private fun changeBuildOptionsVisible() {
        binding.apply {
            slideToEnd(clBuildOptions, root)
            binding.ivReactionSettings
                .setBackgroundResource(getArrowResourceId(clBuildOptions.isVisible))
        }
        uiSettings.setShowBuildoptions(binding.clBuildOptions.isVisible)
    }

    fun getArrowResourceId(isViewVisible: Boolean) =
        if (isViewVisible) R.drawable.ic_arrow_up
        else R.drawable.ic_arrow_down

}