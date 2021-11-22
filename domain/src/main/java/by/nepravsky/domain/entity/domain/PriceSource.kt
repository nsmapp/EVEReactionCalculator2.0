package by.nepravsky.domain.entity.domain

sealed class PriceSource(val id: Int, val name: String){
    object EVE_TECH: PriceSource(1, "esi.evetech.net")
    object EVE_MARKETER: PriceSource(2, "evemarketer.com")
    object DONT_UPDATE: PriceSource(3, "offline mod")



}
