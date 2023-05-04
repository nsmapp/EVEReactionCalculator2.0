package by.nepravsky.sm.evereactioncalculator.di

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.request.CachePolicy
import coil.util.CoilUtils
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                presentationModule,
                domainModule,
                dataModule,
            )


        }

        val imageLoader = ImageLoader.Builder(applicationContext)
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()
        Coil.setImageLoader(imageLoader)
    }


}