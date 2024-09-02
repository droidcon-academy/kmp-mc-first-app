package com.droidcon.doggodelight

import android.app.Application
import com.droidcon.doggodelight.di.initKoin

class DoggoDelightApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
