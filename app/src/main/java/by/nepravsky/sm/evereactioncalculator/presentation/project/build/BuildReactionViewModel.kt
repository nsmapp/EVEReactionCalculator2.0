package by.nepravsky.sm.evereactioncalculator.presentation.project.build

import BaseReactionUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.nepravsky.domain.entity.base.ProjectItem
import by.nepravsky.domain.entity.presenter.ProjectType
import by.nepravsky.domain.entity.presenter.ReactionInfo
import by.nepravsky.domain.entity.request.ItemRequest
import by.nepravsky.domain.entity.request.ProjectRequest
import by.nepravsky.domain.entity.request.ReactionRequest
import by.nepravsky.domain.entity.request.Settings
import by.nepravsky.domain.usecase.FullReactionUseCase
import by.nepravsky.domain.usecase.GetSettingsUseCase
import by.nepravsky.domain.usecase.UpdatePriceUseCase
import by.nepravsky.domain.usecase.productline.GetProjectsItemsUseCase
import by.nepravsky.domain.utils.excepts.EmptyDateException
import by.nepravsky.domain.utils.parseToInt
import by.nepravsky.sm.evereactioncalculator.R
import by.nepravsky.sm.evereactioncalculator.presentation.project.build.mapper.ShareReactionMapper
import by.nepravsky.sm.evereactioncalculator.utils.ViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class BuildReactionViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val baseReactionUseCase: BaseReactionUseCase,
    private val fullReactionUseCase: FullReactionUseCase,
    private val updatePriceUseCase: UpdatePriceUseCase,
    private val getProjectsItemsUseCase: GetProjectsItemsUseCase,
    private val shareReactionMapper: ShareReactionMapper
) : ViewModel() {

    var projectType: ProjectType = ProjectType.Single
    var isFullChain: Boolean = false
    var reactionId: Int = -1
    var run: Int = 1

    private var _settings = Settings()

    private val _state = MutableStateFlow<ViewState<ReactionInfo>>(ViewState.Loading)
    val state = _state.asStateFlow()

    fun loadSettings() {
        viewModelScope.launch {
            getSettingsUseCase.get().collect(
                Success = { flow -> handleLoadSettingsSuccess(flow) },
                Error = {}
            )
        }
    }

    private suspend fun handleLoadSettingsSuccess(flow: Flow<Settings>) {
        flow.catch { Settings() }.collect {
            _settings = it
            runReaction()
        }
    }

    fun runReaction() {
        _state.value = ViewState.Loading
        if (reactionId == -1) {
            _state.value =
                ViewState.Error(R.string.feature_build_reaction_error_failed_create_project)
            return
        }
        when (projectType) {
            ProjectType.Single -> runSingleReaction()
            ProjectType.Project -> getProjectReaction()
        }
    }

    private fun runSingleReaction() {
        if (isFullChain) runFullSingleReaction()
        else runBaseSingleReaction()

    }

    private fun runBaseSingleReaction() {
        viewModelScope.launch {
            updatePriceUseCase.updatePrice(ItemRequest(reactionId), _settings)
            baseReactionUseCase.run(ReactionRequest(reactionId, run), _settings)
                .collect(
                    Success = { setSuccessState(it) },
                    Error = { println("!!! -> ${it.message}") }
                )
        }
    }

    private fun runFullSingleReaction() {
        viewModelScope.launch {
            updatePriceUseCase.updatePrice(ItemRequest(reactionId), _settings)
            fullReactionUseCase.run(ReactionRequest(reactionId, run), _settings).collect(
                Success = { setSuccessState(it) },
                Error = { println("!!! -> ${it.message}") }
            )
        }
    }

    private fun getProjectReaction() {
        viewModelScope.launch {
            getProjectsItemsUseCase.getByParentId(ProjectRequest(reactionId), _settings)
                .collect(
                    Success = { runProjectReaction(it) },
                    Error = { handleGetProjectReactionError(it) }
                )
        }
    }

    private fun handleGetProjectReactionError(it: Exception) {
        if (it is EmptyDateException) _state.value =
            ViewState.Error(R.string.feature_build_reaction_error_empty_project)
    }

    private fun runProjectReaction(items: List<ProjectItem>) {
        if (isFullChain) runFullProjectReaction(items)
        else runBaseProjectReaction(items)
    }

    private fun runBaseProjectReaction(items: List<ProjectItem>) {
        viewModelScope.launch {
            updatePriceUseCase.updatePrice(items.map { ItemRequest(it.reactionId) }, _settings)
            baseReactionUseCase.run(
                items.map { ReactionRequest(it.reactionId, it.run) }, _settings
            ).collect(
                Success = { setSuccessState(it) },
                Error = {}
            )
        }
    }

    private fun runFullProjectReaction(items: List<ProjectItem>) {
        viewModelScope.launch {
            updatePriceUseCase.updatePrice(items.map { ItemRequest(it.reactionId) }, _settings)
            fullReactionUseCase.run(
                items.map { ReactionRequest(it.reactionId, it.run) },
                _settings
            ).collect(
                Success = { setSuccessState(it) },
                Error = {}
            )
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

    fun shareReaction(isSimpleText: Boolean) {
        val state = _state.value
        if (state is ViewState.Success) {
            val reaction =
                if (isSimpleText) shareReactionMapper.createShareSimpleReaction(state.data)
                else shareReactionMapper.createShareEveReaction(state.data)
            _state.value = ViewState.ShareReaction(reaction)
        }
    }

    private fun setSuccessState(reactionInfo: ReactionInfo) {
        _state.value = ViewState.Success(reactionInfo)
    }

}


