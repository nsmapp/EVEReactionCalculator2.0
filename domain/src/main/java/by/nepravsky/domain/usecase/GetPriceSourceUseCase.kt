package by.nepravsky.domain.usecase

import by.nepravsky.domain.entity.base.PriceSource
import by.nepravsky.domain.repository.PriceSourceRepository
import by.nepravsky.domain.utils.Result
import by.nepravsky.domain.utils.runFun
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext


class GetPriceSourceUseCase(private val priceSourceRepository: PriceSourceRepository) {


   suspend fun getAll():Result<List<PriceSource>> =
       runFun { get() }


    private suspend fun get(): List<PriceSource> =
        withContext(IO){
            priceSourceRepository.getAll()
        }

}