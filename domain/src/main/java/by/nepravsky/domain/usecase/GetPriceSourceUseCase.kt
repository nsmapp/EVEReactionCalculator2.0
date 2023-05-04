package by.nepravsky.domain.usecase

import by.nepravsky.domain.entity.Answer
import by.nepravsky.domain.entity.base.PriceSource
import by.nepravsky.domain.repository.PriceSourceRepository
import by.nepravsky.domain.utils.runFunc


class GetPriceSourceUseCase(private val priceSourceRepository: PriceSourceRepository) {


    suspend fun getAll(): Answer<List<PriceSource>> =
        runFunc { priceSourceRepository.getAll() }


}