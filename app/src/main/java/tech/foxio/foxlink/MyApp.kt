package tech.foxio.foxlink

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import tech.foxio.netbirdlib.NetbirdModule
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application(){
//    @Inject
//    lateinit var netbirdModule: NetbirdModule
    override fun onCreate() {
        super.onCreate()
//        netbirdModule.startService()
    }
}