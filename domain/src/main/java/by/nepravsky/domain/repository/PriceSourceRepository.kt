package by.nepravsky.domain.repository

import by.nepravsky.domain.entity.base.PriceSource

interface PriceSourceRepository {


    suspend fun getAll(): List<PriceSource>
}