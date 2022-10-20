package com.example.androidtest.di.module

import com.example.androidtest.data.api.UserApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideClient() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val clientBuilder = OkHttpClient().newBuilder()

        return clientBuilder
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient : OkHttpClient
    ) : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://firebasestorage.googleapis.com/v0/b/candidates--questionnaire.appspot.com/o/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApi(
        retrofit: Retrofit
    ) : UserApi {
        return retrofit.create(UserApi::class.java)
    }

}