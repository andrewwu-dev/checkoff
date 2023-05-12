package com.andrew.checkoff.core.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideApiService(moshi: Moshi): ApiService =
        Retrofit.Builder()
            .run {
                baseUrl(ApiConstants.BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
            }.create(ApiService::class.java)
}