package com.nekkiichi.edusign.data

import android.content.Context
import com.nekkiichi.edusign.data.local.AuthManager
import com.nekkiichi.edusign.data.remote.ApiConfig
import com.nekkiichi.edusign.data.remote.ApiService
import com.nekkiichi.edusign.data.remote.AuthInterceptor
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
    fun provideAppService(authManager: AuthManager): ApiService = ApiConfig.provideApiService(AuthInterceptor(authManager))

    @Provides
    @Singleton
    fun provideAuthManager(@ApplicationContext context: Context): AuthManager = AuthManager(context)
}