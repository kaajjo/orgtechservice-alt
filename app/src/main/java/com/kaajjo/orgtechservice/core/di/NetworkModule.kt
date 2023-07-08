package com.kaajjo.orgtechservice.core.di

import com.kaajjo.orgtechservice.data.remote.api.auth.AuthService
import com.kaajjo.orgtechservice.data.remote.api.info.TariffService
import com.kaajjo.orgtechservice.data.remote.api.user.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthService(
        retrofit: Retrofit
    ): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideUserService(
        retrofit: Retrofit
    ): UserService = retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideTariffService(
        retrofit: Retrofit
    ): TariffService = retrofit.create(TariffService::class.java)
}