package by.nepravsky.data.network.marketer

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@ExperimentalSerializationApi
class MarketerService {

    fun create(timeOut: Long = 20): MarketerApi {

        val contentType = "application/json".toMediaType()

        val interceptor = HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger{
                override fun log(message: String) {
                    Log.d("intercept", "okhttp log: $message")
                }
            }
        )
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .writeTimeout(timeOut, TimeUnit.SECONDS)
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory(contentType))
            .baseUrl("https://api.evemarketer.com/ec/")
            .client(okHttpClient)
            .build()
            .create(MarketerApi::class.java)
    }
}