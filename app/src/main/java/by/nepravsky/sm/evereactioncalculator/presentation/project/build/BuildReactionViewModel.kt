package by.nepravsky.sm.evereactioncalculator.presentation.project.build

import BaseReactionUseCase
import FullReactionUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.domain.entity.presenter.ProjectType
import by.nepravsky.domain.entity.presenter.ReactionInfo
import by.nepravsky.domain.entity.request.ItemRequest
import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.entity.request.ReactionRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.usecase.productline.GetProjectsItemsUseCase
import by.nepravsky.domain.usecase.GetSettingsUseCase
import by.nepravsky.domain.usecase.UpdatePriceUseCase
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.excepts.EmptyDateException
import by.nepravsky.domain.utils.parseToInt
import by.nepravsky.sm.evereactioncalculator.utils.events.Event
import by.nepravsky.sm.evereactioncalculator.utils.events.EventShowSnackBar
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BuildReactionViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val baseReactionUseCase: BaseReactionUseCase,
    private val fullReactionUseCase: FullReactionUseCase,
    private val updatePriceUseCase: UpdatePriceUseCase,
    private val getProjectsItemsUseCase: GetProjectsItemsUseCase
) : ViewModel() {

    var projectTypeId = ProjectType.REACTION_SINGLE.id
    var isFullChain: Boolean = false
    var reactionId: Int = -1
    var run: Int = 1

    private var _settings = Settings()

    private val _progress = MutableStateFlow(false)
    val progress = _progress.asStateFlow()
    private val _reactionInfo = MutableStateFlow<ReactionInfo?>(null)
    val reactionInfo = _reactionInfo.asStateFlow()
    private val _eventList = MutableSharedFlow<Event>()
    val eventList = _eventList.asSharedFlow()

    fun loadSettings() {
        viewModelScope.launch {
            withContext(Main) {
                when (val request = getSettingsUseCase.get()) {
                    is Result.Success -> {
                        request.data
                            .catch { Settings() }
                            .collect {
                                _settings = it
                                runReaction()
                            }
                    }
                    is Result.Error -> {
                        _settings = Settings()
                    }
                }
            }
        }
    }


    fun runReaction() {
        if (reactionId == -1) {
            _eventList.tryEmit(EventShowSnackBar("Error: Failed to create a project"))
            return
        }


        showProgress()
        when (projectTypeId) {
            ProjectType.REACTION_SINGLE.id -> {
                runSingleReaction()
            }
            ProjectType.REACTION_PROJECT.id -> {
                getProjectReaction()
            }
        }
    }

    private fun runSingleReaction() {
        if (isFullChain) runFullSingleReaction()
        else runBaseSingleReaction()

    }

    private fun runBaseSingleReaction() {
        viewModelScope.launch {
            withContext(Main) {
                updatePriceUseCase.updatePrice(ItemRequest(reactionId), _settings)
                when (val request =
                    baseReactionUseCase.run(ReactionRequest(reactionId, run), _settings)) {
                    is Result.Success -> {
                        _reactionInfo.value = request.data
                        stopProgress()
                    }
                    is Result.Error -> {
                        stopProgress()
                    }
                }
            }
        }
    }

    private fun runFullSingleReaction() {
        viewModelScope.launch {
            withContext(Main) {
                updatePriceUseCase.updatePrice(ItemRequest(reactionId), _settings)
                when (val request =
                    fullReactionUseCase.run(ReactionRequest(reactionId, run), _settings)) {
                    is Result.Success -> {
                        _reactionInfo.value = request.data
                        stopProgress()
                    }
                    is Result.Error -> {
                        stopProgress()
                    }
                }
            }
        }
    }

    private fun getProjectReaction() {
        viewModelScope.launch {
            withContext(Main) {
                val request = getProjectsItemsUseCase.getByParentId(
                    ProjectRequest(reactionId), _settings
                )
                when (request) {
                    is Result.Success -> {
                        runProjectReaction(request.data)
                    }
                    is Result.Error -> {
                        if (request.exception is EmptyDateException)
                            _eventList.emit(EventShowSnackBar("Project is empty"))
                        stopProgress()
                    }
                }
            }
        }
    }

    private fun runProjectReaction(items: List<ProjectItem>) {
        if (isFullChain) runFullProjectReaction(items)
        else runBaseProjectReaction(items)
    }

    private fun runBaseProjectReaction(items: List<ProjectItem>) {
        viewModelScope.launch {
            withContext(Main) {
                updatePriceUseCase.updatePrice(items.map { ItemRequest(it.reactionId) }, _settings)
                val request = baseReactionUseCase.run(
                    items.map { ReactionRequest(it.reactionId, it.run) }, _settings
                )
                when (request) {
                    is Result.Success -> {
                        _reactionInfo.value = request.data
                        stopProgress()
                    }
                    is Result.Error -> {
                        stopProgress()
                    }
                }
            }

        }
    }

    private fun runFullProjectReaction(items: List<ProjectItem>) {
        viewModelScope.launch {
            withContext(Main) {
                updatePriceUseCase.updatePrice(items.map { ItemRequest(it.reactionId) }, _settings)
                val request = fullReactionUseCase.run(
                    items.map { ReactionRequest(it.reactionId, it.run) }, _settings
                )
                when (request) {
                    is Result.Success -> {
                        _reactionInfo.value = request.data
                        stopProgress()
                    }
                    is Result.Error -> {
                        stopProgress()
                    }
                }
            }
        }
    }

    fun changeME(me: CharSequence?) {
        _settings.me = me.parseToInt(0)
        runReaction()
    }

    fun changeSubMe(subME: CharSequence?) {
        _settings.subME = subME.parseToInt(0)
        runReaction()
    }

    fun changeRunCount(runs: CharSequence?) {
        run = runs.parseToInt(1)
        runReaction()
    }


    fun stopProgress() {
        _progress.value = false
    }

    fun showProgress() {
        _progress.value = true
    }

}


