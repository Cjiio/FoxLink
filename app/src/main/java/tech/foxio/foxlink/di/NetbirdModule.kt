package tech.foxio.foxlink.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.foxio.netbirdlib.NetbirdModule
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object NetbirdModule {
////    @Provides
////    @Singleton
////    fun provideNetbirdModule(@ApplicationContext context: Context) : NetbirdModule {
////        return NetbirdModule(context)
////    }
//}