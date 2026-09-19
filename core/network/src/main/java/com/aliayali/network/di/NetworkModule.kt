package com.aliayali.network.di

import com.aliayali.network.BuildConfig
import com.aliayali.network.retrofit.BrsApi
import com.aliayali.network.retrofit.CoinGeckoApi
import com.aliayali.network.retrofit.NewsApi
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

private const val COIN_GECKO_BASE_URL =
    "https://api.coingecko.com/api/v3/"

private const val BRS_BASE_URL =
    "https://Api.BrsApi.ir/"

private const val NEWS_BASE_URL =
    "https://newsapi.org/"

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
    @NewsRetrofit
    fun providesNewsOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader(
                        "X-Api-Key",
                        BuildConfig.NEWS_API_KEY,
                    )
                    .build()

                chain.proceed(request)
            }
            .build()

    @Provides
    @Singleton
    @CoinGeckoRetrofit
    fun providesCoinGeckoRetrofit(
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
    @BrsRetrofit
    fun providesBrsRetrofit(
        networkJson: Json,
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BRS_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                networkJson.asConverterFactory(
                    "application/json".toMediaType(),
                ),
            )
            .build()

    @Provides
    @Singleton
    @NewsRetrofit
    fun providesNewsRetrofit(
        networkJson: Json,
        @NewsRetrofit okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(NEWS_BASE_URL)
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
        @CoinGeckoRetrofit retrofit: Retrofit,
    ): CoinGeckoApi =
        retrofit.create(CoinGeckoApi::class.java)

    @Provides
    @Singleton
    fun providesBrsApi(
        @BrsRetrofit retrofit: Retrofit,
    ): BrsApi =
        retrofit.create(BrsApi::class.java)

    @Provides
    @Singleton
    fun providesNewsApi(
        @NewsRetrofit retrofit: Retrofit,
    ): NewsApi =
        retrofit.create(NewsApi::class.java)
}