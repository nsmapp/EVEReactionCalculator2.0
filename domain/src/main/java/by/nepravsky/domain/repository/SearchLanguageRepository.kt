package by.nepravsky.domain.repository

import by.nepravsky.domain.entity.presenter.SearchLanguage

interface SearchLanguageRepository {

    suspend fun getAll(): List<SearchLanguage>
}