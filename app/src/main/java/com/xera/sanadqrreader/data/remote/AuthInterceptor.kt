package com.xera.sanadqrreader.data.remote

import android.content.SharedPreferences
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val userToken = sharedPreferences.getString("token",null)
        val request = chain.request()
            .newBuilder()
            .cacheControl(cacheControl)
            .header("Authorization", "bearer $userToken")
            .build()
        return chain.proceed(request)
    }


    private val cacheControl = CacheControl.Builder()
        .maxAge(1, TimeUnit.HOURS)
        .build()

}