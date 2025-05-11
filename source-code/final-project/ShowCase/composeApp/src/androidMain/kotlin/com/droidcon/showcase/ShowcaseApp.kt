package com.droidcon.showcase

import android.app.Application
import org.koin.android.ext.koin.androidContext
import com.droidcon.showcase.di.initKoin

class ShowcaseApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@ShowcaseApp)
        }
    }
}