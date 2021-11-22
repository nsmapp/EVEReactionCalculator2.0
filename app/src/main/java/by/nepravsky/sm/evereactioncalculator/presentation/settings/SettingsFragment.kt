package by.nepravsky.sm.evereactioncalculator.presentation.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.nepravsky.sm.evereactioncalculator.R
import by.nepravsky.sm.evereactioncalculator.databinding.SettingsFragmentBinding
import by.nepravsky.domain.entity.base.PriceSource
import by.nepravsky.domain.entity.base.SolarSystem
import by.nepravsky.domain.entity.presenter.SearchLanguage
import by.nepravsky.sm.evereactioncalculator.utils.views.DropDownAdapter
import by.nepravsky.sm.evereactioncalculator.utils.listnersinterface.ItemClickListener
import by.nepravsky.sm.evereactioncalculator.utils.UISettings
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class SettingsFragment : Fragment() {


    private val uiSettings: UISettings by inject()
    private val viewModel: SettingsViewModel by inject()
    private lateinit var binding: SettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSettings()
        binding.swNightMode.isChecked = uiSettings.isNightThem()

        initListeners()
        initFlow()

    }

    private fun initListeners() {
        binding.swNightMode.setOnCheckedChangeListener { _, b ->
            if (b) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            uiSettings.setNightThem(b)
        }

        binding.lAbout.setOnClickListener {
            val action = SettingsFragmentDirections
                .actionSettingsFragmentToAboutFragment()
            findNavController().navigate(action)
        }
    }


    private fun initFlow() {
        lifecycleScope.launchWhenStarted {

            viewModel.languages.onEach { setLanguageDropDown(it) }
                .launchIn(lifecycleScope)

            viewModel.solarSystems.onEach { setSolarSystemsDropDown(it) }
                .launchIn(lifecycleScope)

            viewModel.priceSources.onEach { setPriceSourceDropDown(it) }
                .launchIn(lifecycleScope)

            viewModel.settings.onEach {
                it?.let {
                    viewModel.getLanguage()
                    viewModel.getSolarSystems()
                    viewModel.getPriceSources()

                }
            }.launchIn(lifecycleScope)

        }
    }


    private fun setLanguageDropDown(languageLst: List<SearchLanguage>){
        val dropDownAdapter = DropDownAdapter<SearchLanguage>(
            requireContext(),
            R.layout.item_drop_down_list,
            languageLst,
            object : ItemClickListener<SearchLanguage> {
                override fun onItemClick(item: SearchLanguage) {
                    binding.etLanguage.setText(item.name, false)
                    viewModel.changeSearchLanguage(item)
                    binding.etLanguage.dismissDropDown()
                }

            })

        val selected = languageLst.find { it.id == viewModel.settings.value?.languageId }?.name ?: ""
        binding.etLanguage.apply {
            setAdapter(dropDownAdapter)
            setText(selected, false)
        }

    }

    private fun setSolarSystemsDropDown(solarSystems: List<SolarSystem>){
        val dropDownAdapter = DropDownAdapter<SolarSystem>(
            requireContext(),
            R.layout.item_drop_down_list,
            solarSystems,
            object : ItemClickListener<SolarSystem> {
                override fun onItemClick(item: SolarSystem) {
                    binding.etSolarSystem.setText(item.name, false)
                    viewModel.changeSolarSystem(item)
                    binding.etSolarSystem.dismissDropDown()
                }
            })

        val selected = solarSystems.find { it.id == viewModel.settings.value?.systemId }?.name ?: ""
        binding.etSolarSystem.apply {
            setAdapter(dropDownAdapter)
            setText(selected, false)
        }

    }

    private fun setPriceSourceDropDown(priceSources: List<PriceSource>){
        val dropDownAdapter = DropDownAdapter<PriceSource>(
            requireContext(),
            R.layout.item_drop_down_list,
            priceSources,
            object : ItemClickListener<PriceSource> {
                override fun onItemClick(item: PriceSource) {
                    binding.etPriceSource.setText(item.name, false)
                    viewModel.changePriceSource(item)
                    binding.etPriceSource.dismissDropDown()
                }
            })

        val selected = priceSources.find { it.id == viewModel.settings.value?.priceUpdateSource }?.name ?: ""
        binding.etPriceSource.apply {
            setAdapter(dropDownAdapter)
            setText(selected, false)
        }

    }




}