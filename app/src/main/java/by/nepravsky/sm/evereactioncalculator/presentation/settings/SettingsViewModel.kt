package by.nepravsky.sm.evereactioncalculator.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.nepravsky.domain.entity.base.PriceSource
import by.nepravsky.domain.entity.base.SolarSystem
import by.nepravsky.domain.entity.presenter.SearchLanguage
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.usecase.GetPriceSourceUseCase
import by.nepravsky.domain.usecase.GetSearchLanguageUseCase
import by.nepravsky.domain.usecase.GetSettingsUseCase
import by.nepravsky.domain.usecase.GetSolarSystemsUseCase
import by.nepravsky.domain.usecase.SaveSettingsUseCase
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val getAllLanguageUseCase: GetSearchLanguageUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getSolarSystemsUseCase: GetSolarSystemsUseCase,
    private val getPriceSourceUseCase: GetPriceSourceUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase
) : ViewModel() {

    private val _settings = MutableStateFlow<Settings?>(null)
    val settings = _settings.asStateFlow()

    private val _language = MutableStateFlow<List<SearchLanguage>>(emptyList())
    val languages = _language.asStateFlow()

    private val _solarSystem = MutableStateFlow<List<SolarSystem>>(emptyList())
    val solarSystems = _solarSystem.asStateFlow()

    private val _priceSource = MutableStateFlow<List<PriceSource>>(emptyList())
    val priceSources = _priceSource.asStateFlow()


    fun getSettings() {
        viewModelScope.launch {
            getSettingsUseCase.get().collect(
                Success = { flow -> flow.catch { Settings() }.collect { _settings.value = it } },
                Error = {}
            )
        }
    }

    fun getLanguage() {
        viewModelScope.launch {
            getAllLanguageUseCase.getAll().collect(
                Success = { _language.value = it },
                Error = {}
            )
        }
    }


    fun getSolarSystems() {
        viewModelScope.launch {
            getSolarSystemsUseCase.getAll().collect(
                Success = { _solarSystem.value = it },
                Error = {}
            )
        }
    }

    fun getPriceSources() {
        viewModelScope.launch {
            withContext(Main) {
                getPriceSourceUseCase.getAll().collect(
                    Success = { _priceSource.value = it },
                    Error = {}
                )
            }
        }
    }

    fun changeSearchLanguage(searchLanguage: SearchLanguage) {
        _settings.value?.let {
            saveSettings(it.apply { languageId = searchLanguage.id })
        }
    }

    fun changePriceSource(priceSource: PriceSource) {
        _settings.value?.let {
            saveSettings(it.apply { priceUpdateSource = priceSource.id })
        }
    }

    fun changeSolarSystem(solarSystem: SolarSystem) {
        _settings.value?.let {
            saveSettings(it.apply {
                systemId = solarSystem.id
                regionId = solarSystem.regionId
            })
        }
    }

    private fun saveSettings(settings: Settings) {
        viewModelScope.launch {
            saveSettingsUseCase.save(settings).collect(
                Success = {},
                Error = {}
            )
        }
    }
}