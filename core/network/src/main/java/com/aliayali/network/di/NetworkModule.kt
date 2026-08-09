package com.aliayali.network.di

import com.aliayali.network.BuildConfig
import com.aliayali.network.CoinGeckoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

private const val COIN_GECKO_BASE_URL = "https://api.coingecko.com/api/v3/"

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader(
                        "x-cg-demo-api-key",
                        BuildConfig.COIN_GECKO_API_KEY,
                    )
                    .build()

                chain.proceed(request)
            }
            .build()

    @Provides
    @Singleton
    fun providesRetrofit(
        networkJson: Json,
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(COIN_GECKO_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                networkJson.asConverterFactory(
                    "application/json".toMediaType(),
                ),
            )
            .build()

    @Provides
    @Singleton
    fun providesCoinGeckoApi(
        retrofit: Retrofit,
    ): CoinGeckoApi =
        retrofit.create(CoinGeckoApi::class.java)
}