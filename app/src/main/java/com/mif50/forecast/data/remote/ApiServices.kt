package com.mif50.forecast.data.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mif50.forecast.data.remote.model.CurrentWeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// http://api.apixu.com/v1/current.json?key=a984f0dc712a4873b12144231181512&q=lodon

// a984f0dc712a4873b12144231181512
const val API_KEY = "a984f0dc712a4873b12144231181512"

interface ApiServices {

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): ApiServices {
            val requestInterceptor = Interceptor {
                val url = it.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("key", API_KEY)
                        .build()

                val request = it.request()
                        .newBuilder()
                        .url(url)
                        .build()

                return@Interceptor it.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .addInterceptor(connectivityInterceptor)
                    .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://api.apixu.com/v1/")
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServices::class.java)
        }
    }

    @GET("current.json")
    fun getCurrentWeatherAsync(
            @Query("q") location: String,
            @Query("lang") langCode: String = "en"): Deferred<CurrentWeatherResponse>
}