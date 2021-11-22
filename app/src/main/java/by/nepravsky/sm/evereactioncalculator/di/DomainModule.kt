package by.nepravsky.sm.evereactioncalculator.di

import BaseReactionUseCase
import FullReactionUseCase
import by.nepravsky.domain.usecase.*
import by.nepravsky.domain.usecase.productline.*
import org.koin.dsl.module

val domainModule = module{

    factory<UpdatePriceUseCase> {
        UpdatePriceUseCase(
            reactionRepository = get(),
            priceRepository = get()
        )
    }

    factory<BaseReactionUseCase> {
        BaseReactionUseCase(
            reactionRepository = get(),
            itemRepository = get()
        )
    }

    factory<FullReactionUseCase> {
        FullReactionUseCase(
            reactionRepository = get(),
            itemRepository = get()
        )
    }

    factory<SearchReactionUseCase> { SearchReactionUseCase(reactionRepository =  get()) }

    factory<GetSettingsUseCase> { GetSettingsUseCase(settingsRepository = get()) }

    factory<GetItemGroupsUseCase> { GetItemGroupsUseCase(itemGroupRepo = get()) }

    factory<GetSearchLanguageUseCase> {GetSearchLanguageUseCase(searchLanguageRepository = get()) }

    factory<GetSolarSystemsUseCase> { GetSolarSystemsUseCase(solarSystemsRepository = get()) }

    factory<GetPriceSourceUseCase> { GetPriceSourceUseCase(priceSourceRepository = get()) }

    factory<SaveSettingsUseCase> { SaveSettingsUseCase(settingsRepository = get()) }

    factory<GetAllProjectsUseCase> { GetAllProjectsUseCase(projectRepository = get()) }

    factory<GetProjectsItemsUseCase> { GetProjectsItemsUseCase(projectItemRepository = get()) }

    factory<DeleteProjectUseCase> {
        DeleteProjectUseCase(projectRepository = get(), projectItemRepository = get())
    }

    factory<DeleteProjectItemUseCase> { DeleteProjectItemUseCase(projectItemRepository = get()) }

    factory<SaveProjectItemUseCase> { SaveProjectItemUseCase(projectItemRepository = get()) }

    factory<SaveProjectUseCase> { SaveProjectUseCase(projectRepository = get()) }

    factory<GetProjectByIdUseCase> { GetProjectByIdUseCase(projectRepository = get()) }
}

