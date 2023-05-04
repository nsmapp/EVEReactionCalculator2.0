package by.nepravsky.data.repoimpl

import by.nepravsky.data.database.repository.LanguageDAORepository
import by.nepravsky.domain.entity.presenter.SearchLanguage
import by.nepravsky.domain.repository.SearchLanguageRepository

class SearchLanguageRepoImpl(private val languageDAORepository: LanguageDAORepository) :
    SearchLanguageRepository {

    override fun getAll(): List<SearchLanguage> = languageDAORepository.getAll()
}