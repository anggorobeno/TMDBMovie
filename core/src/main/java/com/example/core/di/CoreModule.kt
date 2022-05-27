package com.example.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.core.BuildConfig
import com.example.core.data.network.NetworkInterceptor
import com.example.core.data.remote.services.TMDBApiService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {
  @Provides
  @Singleton
  fun providesBaseUrl(): String {
    return BuildConfig.BASE_URL
  }

  @Provides
  @Singleton
  fun providesOkHttpClient(@ApplicationContext context: Context, authInterceptor: NetworkInterceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
    try {
      builder.addNetworkInterceptor(authInterceptor)
      if (BuildConfig.DEBUG){
        builder.addInterceptor(HttpLoggingInterceptor().setLevel(level = BODY))
        builder.addInterceptor(ChuckerInterceptor(context))
      }
      return builder.build()
    }
    catch (e: Exception){
      throw RuntimeException(e)
    }
  }

  @Provides
  @Singleton
  fun provideRetrofit(client: OkHttpClient,baseUrl: String): Retrofit{

    return Retrofit.Builder()
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .baseUrl(baseUrl)
      .client(client)
      .build()
  }

  @Provides
  @Singleton
  fun provideApiService(retrofit: Retrofit): TMDBApiService = retrofit.create(TMDBApiService::class.java)
}