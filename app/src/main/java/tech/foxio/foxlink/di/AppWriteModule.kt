package tech.foxio.foxlink.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.appwrite.Client
import tech.foxio.foxlink.BuildConfig
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppWriteModule {

    @Singleton
    @Provides
    fun provideAppWriteModule(@ApplicationContext context: Context): Client {
        return Client(context)
            .setEndpoint(BuildConfig.APP_WRITE_BASE_URL)
            .setProject(BuildConfig.APP_WRITE_PROJECT_ID)
    }
}