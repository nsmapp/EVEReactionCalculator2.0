package by.nepravsky.domain.repository

import by.nepravsky.domain.entity.presenter.SearchLanguage

interface SearchLanguageRepository {

    fun getAll(): List<SearchLanguage>
}