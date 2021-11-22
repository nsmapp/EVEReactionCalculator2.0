package by.nepravsky.sm.evereactioncalculator.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.nepravsky.domain.entity.base.PriceSource
import by.nepravsky.domain.entity.base.SolarSystem
import by.nepravsky.domain.entity.presenter.SearchLanguage
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.usecase.*
import by.nepravsky.domain.utils.Result
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
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


    fun getSettings(){
        viewModelScope.launch {
            withContext(Main){
                val settings = getSettingsUseCase.get()
                when(settings){
                    is Result.Success ->{
                        settings.data.catch { Settings() }.collect { _settings.value = it }
                    }
                    is Result.Error -> {}
                }
            }
        }
    }

    fun getLanguage(){
        viewModelScope.launch {
            withContext(Main){
                val lanList = getAllLanguageUseCase.getAll()
                when(lanList){
                    is Result.Success -> {
                        _language.value = lanList.data
                    }
                    is Result.Error -> {}
                }
            }
        }
    }


    fun getSolarSystems(){
        viewModelScope.launch {
            withContext(Main){
                val request = getSolarSystemsUseCase.getAll()
                when(request){
                    is Result.Success -> {
                        _solarSystem.value = request.data
                    }
                    is Result.Error -> {}
                }
            }
        }
    }

    fun getPriceSources(){
        viewModelScope.launch {
            withContext(Main){
                val request = getPriceSourceUseCase.getAll()
                when(request){
                    is Result.Success -> {
                        _priceSource.value = request.data
                    }
                    is Result.Error -> {}
                }
            }
        }
    }

    fun changeSearchLanguage(searchLanguage: SearchLanguage){
        _settings.value?.let {
            saveSettings(it.apply { languageId = searchLanguage.id })
        }
    }

    fun changePriceSource(priceSource: PriceSource){
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

    private fun saveSettings(settings: Settings){
        viewModelScope.launch {
            withContext(Main){
                val request = saveSettingsUseCase.save(settings)
            }
        }
    }
}