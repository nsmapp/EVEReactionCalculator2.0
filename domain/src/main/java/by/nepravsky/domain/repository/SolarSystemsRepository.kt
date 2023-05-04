package by.nepravsky.domain.repository

import by.nepravsky.domain.entity.base.SolarSystem

interface SolarSystemsRepository {

    fun getAll(): List<SolarSystem>
}