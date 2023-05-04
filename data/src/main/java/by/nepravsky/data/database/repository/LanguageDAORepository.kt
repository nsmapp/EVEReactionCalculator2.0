package by.nepravsky.data.database.repository

import by.nepravsky.domain.entity.presenter.SearchLanguage

interface LanguageDAORepository {

    fun getAll(): List<SearchLanguage>
}