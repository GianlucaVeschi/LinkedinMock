package com.gianlucaveschi.linkedinmock.di

import android.content.Context
import com.gianlucaveschi.linkedinmock.network.LinkedinService
import com.gianlucaveschi.linkedinmock.network.interceptors.AuthInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val AWS_DB = "https://wgz27h2c1j.execute-api.us-east-1.amazonaws.com/"
const val AUTH_TOKEN = "XWRavjM@aRhYE8n@T!GQXx6uGw3*TbnbA6Nsyx-8p9RN.4EVW96FxJJmrRBbFo*s*VuacHroKHr"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @ApplicationContext appContext: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor("Bearer", AUTH_TOKEN))
            .connectTimeout(30, TimeUnit.SECONDS) //If Backend is really slow
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitLinkedinService(okHttpClient: OkHttpClient): LinkedinService {
        return Retrofit.Builder()
            .baseUrl(AWS_DB)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(LinkedinService::class.java)
    }
}