package by.nepravsky.data.network.evetech

import by.nepravsky.data.network.evetech.entity.EveTechPriceResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EveTechApi {
    //up to 200 TypeIDs per request
    @GET("latest/markets/{region_id}/orders/")
    fun getPricesAsync(
        @Path("region_id") regionId: Int,
        @Query("type_id") typeId: Int,
        @Query("datasource") datasource: String = "tranquility",
        @Query("order_type") orderType: String = "all",
    ): Deferred<List<EveTechPriceResponse>>



}