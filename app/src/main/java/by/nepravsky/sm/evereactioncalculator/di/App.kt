package by.nepravsky.sm.evereactioncalculator.di

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(
                by.nepravsky.sm.evereactioncalculator.di.presentationModule,
                by.nepravsky.sm.evereactioncalculator.di.domainModule,
                by.nepravsky.sm.evereactioncalculator.di.dataModule,
            )


        }

        val imageLoader = ImageLoader.Builder(applicationContext)
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(applicationContext))
                    .build()
            }
            .build()
        Coil.setImageLoader(imageLoader)
    }


}