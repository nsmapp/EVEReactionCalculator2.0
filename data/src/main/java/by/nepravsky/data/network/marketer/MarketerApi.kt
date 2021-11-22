package by.nepravsky.data.network.marketer

import by.nepravsky.data.network.marketer.entity.MarketerPriceResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketerApi {
    //up to 200 TypeIDs per request
    @GET("marketstat/json")
    fun getPricesAsync(
            @Query("typeid") typeIds: List<Int>,
            @Query("usesystem") solarSystemId: Int
    ): Deferred<List<MarketerPriceResponse>>



}