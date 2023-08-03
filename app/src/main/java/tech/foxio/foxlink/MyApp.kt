package tech.foxio.foxlink

import android.app.Application
import com.safframework.log.L
import com.safframework.log.LogLevel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // 初始化日志
        initLog()
    }

    private fun initLog() {
        if (BuildConfig.DEBUG) {
            L.logLevel = LogLevel.DEBUG
        } else {
            L.logLevel = LogLevel.ERROR
        }
    }
}