package tech.foxio.foxlink

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import tech.foxio.foxlink.utils.NetbirdModule

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NetbirdModule.Init(this)
    }
}