package com.example.githubuser.modul

import com.example.githubuser.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    fun provideNetworkClient(
        okHttpClient: OkHttpClient
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build().create<ApiService>(ApiService::class.java)
    }

    @Provides
    fun providesClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "token e390a2a4a4d432adeea40fb36938fb487970d71a")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()
    }
}
