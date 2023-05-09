package by.nepravsky.sm.evereactioncalculator.di

import by.nepravsky.sm.evereactioncalculator.presentation.recipe.createitem.ItemCreateViewModel
import by.nepravsky.sm.evereactioncalculator.presentation.project.build.BuildReactionViewModel
import by.nepravsky.sm.evereactioncalculator.presentation.project.build.mapper.ShareReactionMapper
import by.nepravsky.sm.evereactioncalculator.presentation.recipe.productline.ProductLineViewModel
import by.nepravsky.sm.evereactioncalculator.presentation.project.projects.ProjectsViewModel
import by.nepravsky.sm.evereactioncalculator.presentation.recipe.reactor.ReactorViewModel
import by.nepravsky.sm.evereactioncalculator.presentation.settings.SettingsViewModel
import by.nepravsky.sm.evereactioncalculator.utils.UISettings
import org.koin.dsl.module

val presentationModule = module {

    single<UISettings> { UISettings(context = get()) }

    factory<ProjectsViewModel> {
        ProjectsViewModel(
            searchReactionUseCase = get(),
            getSettingsUseCase = get(),
            getAllGroupsUseCase = get(),
            updateGroupSelectionUseCase = get()
        )
    }

    factory<BuildReactionViewModel> {
        BuildReactionViewModel(
            getSettingsUseCase = get(),
            baseReactionUseCase = get(),
            fullReactionUseCase = get(),
            updatePriceUseCase = get(),
            getProjectsItemsUseCase = get(),
            shareReactionMapper = get()
        )
    }

    factory<SettingsViewModel> {
        SettingsViewModel(
            getAllLanguageUseCase = get(),
            getSettingsUseCase = get(),
            getSolarSystemsUseCase = get(),
            getPriceSourceUseCase = get(),
            saveSettingsUseCase = get()
        )
    }

    factory<ReactorViewModel> {
        ReactorViewModel(
            getAllProjectsUseCase = get(),
            deleteProjectUseCase = get(),
            saveProjectUseCase = get()
        )
    }

    factory<ProductLineViewModel> {
        ProductLineViewModel(
            getSettingsUseCase = get(),
            getProjectsItemsUseCase = get(),
            deleteProjectItemUseCase = get(),
            getProjectByIdUseCase = get(),
            saveProjectUseCase = get()
        )
    }

    factory<ItemCreateViewModel> {
        ItemCreateViewModel(
            searchReactionUseCase = get(),
            getSettingsUseCase = get(),
            getAllGroupsUseCase = get(),
            saveProjectItemUseCase = get(),
            updateGroupSelectionUseCase = get()
        )
    }

    single<ShareReactionMapper> { ShareReactionMapper(context = get()) }
}

