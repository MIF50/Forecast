package com.mif50.forecast.data.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mif50.forecast.data.remote.model.CurrentWeatherResponse
import com.mif50.forecast.data.remote.model.FutureWeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


// http://api.apixu.com/v1/current.json?key=a984f0dc712a4873b12144231181512&q=lodon

// a984f0dc712a4873b12144231181512
//const val API_KEY = "a984f0dc712a4873b12144231181512"
const val API_KEY = "d029af7c4886aff71cb664e355e20892"

interface ApiServices {

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): ApiServices {
            val requestInterceptor = Interceptor {
                val url = it.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("access_key", API_KEY)
                    .build()

                val request = it.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor it.proceed(request)
            }


            val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(interceptor)
                .build()




            // http://api.apixu.com/v1/
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.weatherstack.com")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiServices::class.java)
        }
    }

//    @GET("current.json")
    @GET("current")
// @Query("language") languageCode: String = "ar"
    fun getCurrentWeatherAsync(
        @Query("query") location: String
    ): Deferred<CurrentWeatherResponse>

    // https://api.apixu.com/v1/forecast.json?key=a984f0dc712a4873b12144231181512&q=Los%20Angeles&days=1
//    @GET("forecast.json")
    // @Query("language") languageCode: String = "ar"
    @GET("forecast")
    fun getFutureWeatherAsync(
        @Query("query") location: String,
        @Query("forecast_days") days: Int
    ): Deferred<FutureWeatherResponse>
}