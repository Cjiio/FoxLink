package tech.foxio.foxlink

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import tech.foxio.netbirdlib.NetbirdModule
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NetbirdModule.Init(this)
    }
}