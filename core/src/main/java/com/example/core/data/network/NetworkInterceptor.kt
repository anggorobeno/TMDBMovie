package com.example.core.data.network

import com.example.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(): Interceptor {
  override fun intercept(chain: Chain): Response {
    val originalRequest = chain.request()
    val modifiedRequest = originalRequest.newBuilder()
    modifiedRequest.header("Authorization","Bearer " + BuildConfig.apiKey)
    return chain.proceed(modifiedRequest.build())
  }
}