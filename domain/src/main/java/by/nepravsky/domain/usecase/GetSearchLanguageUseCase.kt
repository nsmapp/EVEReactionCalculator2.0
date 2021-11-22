package by.nepravsky.domain.usecase

import by.nepravsky.domain.entity.presenter.SearchLanguage
import by.nepravsky.domain.repository.SearchLanguageRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.runFun
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetSearchLanguageUseCase(private val searchLanguageRepository: SearchLanguageRepository) {


   suspend fun getAll():Result<List<SearchLanguage>> =
       runFun { get() }


    private suspend fun get(): List<SearchLanguage> =
        withContext(IO){
            searchLanguageRepository.getAll()
        }

}