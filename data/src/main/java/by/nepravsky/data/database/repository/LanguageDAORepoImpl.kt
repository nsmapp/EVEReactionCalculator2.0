package by.nepravsky.data.database.repository

import by.nepravsky.data.database.AppDatabase
import by.nepravsky.data.database.entity.Language
import by.nepravsky.domain.entity.presenter.SearchLanguage

class LanguageDAORepoImpl(private val appDatabase: AppDatabase): LanguageDAORepository {

    override fun getAll(): List<SearchLanguage> =
        appDatabase.languageDao().getAll()
            .map { toDomain(it) }


    private fun toDomain(language: Language) : SearchLanguage =
        SearchLanguage(language.id, language.name)
}