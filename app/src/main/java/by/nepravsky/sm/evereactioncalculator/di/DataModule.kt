@file:Suppress("EXPERIMENTAL_API_USAGE")

package by.nepravsky.sm.evereactioncalculator.di

import android.content.Context
import androidx.room.Room
import by.nepravsky.data.database.AppDatabase
import by.nepravsky.data.database.repository.*
import by.nepravsky.data.database.typeconverter.ReactionElementConverter
import by.nepravsky.data.network.evetech.EveTechApi
import by.nepravsky.data.network.evetech.EveTechService
import by.nepravsky.data.network.evetech.repoiml.EveTechRepoImpl
import by.nepravsky.data.network.marketer.MarketerApi
import by.nepravsky.data.network.marketer.MarketerService
import by.nepravsky.data.network.marketer.repoimpl.MarketerRepoImpl
import by.nepravsky.data.network.repository.EveTechRepository
import by.nepravsky.data.network.repository.MarketerRepository
import by.nepravsky.data.repoimpl.*
import by.nepravsky.domain.repository.*
import org.koin.dsl.module

val dataModule = module {

    single<MarketerApi> { MarketerService().create(20)}
    single<EveTechApi>{EveTechService().create(20)}

    single<AppDatabase> { provideDatabase(get())}


    single<PriceRepository>{PriceRepoImpl(
        marketerRepository = get(),
        eveTechRepository = get(),
        priceDAORepository = get()
    )}
    single<PriceDAORepository> {PriceDAORepoImpl(appDatabase = get())}
    single<MarketerRepository>{ MarketerRepoImpl(marketerApi = get()) }
    single<EveTechRepository>{EveTechRepoImpl(eveTechApi = get())}

    single<SolarSystemsRepository>{SolarSystemRepoImpl(appDatabase = get())}

    single<TypeRepository>{TypeRepoImpl(appDatabase = get())}

    single<ItemRepository>{ItemRepoImpl(typeRepository = get())}

    single<ReactionRepository> {ReactionRepoImpl(reactionDAORepository = get())}
    single<ReactionDAORepository> {ReactionDAoRepoImpl(appDatabase = get())}

    single<SetupDAORepository> { SetupDAOImpl(appDatabase = get()) }
    single<SettingsRepository> { SettingsRepoImpl(setupDAORepository = get()) }

    single<GroupDAORepository> { GroupDAORepoImpl(appDatabase = get()) }
    single<ItemGroupRepository> { ItemGroupRepoImpl(groupDAORepository = get()) }

    single<LanguageDAORepository> { LanguageDAORepoImpl(appDatabase = get())}
    single<SearchLanguageRepository> { SearchLanguageRepoImpl(languageDAORepository = get())  }

    single<PriceSourceRepository> {PriceSourceRepoImpl(appDatabase = get())}

    single<ReactionProjectDAORepository> { ReactionProjectDAORepoImpl(appDatabase = get())  }
    single<ProjectRepository>{ ProjectRepoImpl(projectDAORepository = get())}

    single<ReactionProjectItemDaoRepository>{ReactionProjectItemDaoRepoImpl(appDatabase = get())}
    single<ProjectItemRepository> {ProjectItemRepoImpl(reactionProjectItemDaoRepository = get())}

}


private fun provideDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, "reactionsn2new.db")
        .createFromAsset("reactions2dbnew")
        .fallbackToDestructiveMigration()
        .addTypeConverter(ReactionElementConverter())
        .build()
}


