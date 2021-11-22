package by.nepravsky.data.database.repository

import by.nepravsky.domain.entity.presenter.SearchLanguage

interface LanguageDAORepository {

    suspend fun getAll(): List<SearchLanguage>
}