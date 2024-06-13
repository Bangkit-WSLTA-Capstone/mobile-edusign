package com.nekkiichi.edusign.data

import com.nekkiichi.edusign.data.remote.ApiConfig
import com.nekkiichi.edusign.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Injection {

    @Provides
    @Singleton
    fun provideAppService():ApiService = ApiConfig.provideApiService()
}