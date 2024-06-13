package com.nekkiichi.edusign.data

import android.content.Context
import com.nekkiichi.edusign.data.local.AuthDataStore
import com.nekkiichi.edusign.data.remote.ApiConfig
import com.nekkiichi.edusign.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Injection {
    @Provides
    @Singleton
    fun provideAppService(authDataStore: AuthDataStore): ApiService = ApiConfig.provideApiService(authDataStore)

    @Provides
    @Singleton
    fun provideAuthDataStore(@ApplicationContext context: Context): AuthDataStore = AuthDataStore(context)
}