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
        val userToken = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiBUb2tlbiIsImlzcyI6IlhlcmFTZXJ2ZXIiLCJlbWFpbCI6ImhhbmFfaGFueUB5YWhvby5jb20ifQ.GDO0M4D5tCbnyHJUlOMJ3p9JK9KYB9F8M0vc1jf_-lysqBLjdKOU0dW4xYrWEiYoHhvnVXFTfYx_YXzZEh_Ydg"
            //sharedPreferences.getString("token",null)
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