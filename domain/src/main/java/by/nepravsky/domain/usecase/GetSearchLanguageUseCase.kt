package by.nepravsky.domain.usecase

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.entity.presenter.SearchLanguage
import by.nepravsky.domain.repository.SearchLanguageRepository
import by.nepravsky.domain.utils.runFunc

class GetSearchLanguageUseCase(private val searchLanguageRepository: SearchLanguageRepository) {


    suspend fun getAll(): Answer<List<SearchLanguage>> =
        runFunc { searchLanguageRepository.getAll() }

}