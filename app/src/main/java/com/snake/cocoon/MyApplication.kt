package com.snake.cocoon

import android.app.Application
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        Stetho.initialize(
            Stetho.newInitializerBuilder(applicationContext)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(applicationContext))
                .build())
    }
}