package com.example.showgraph.data.remote.di

import com.example.showgraph.data.remote.PointsApi
import com.example.showgraph.data.repository.PointsRepositoryImpl
import com.example.showgraph.domain.repository.PointsRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkInjectionModule {

    val module = module {

        single <PointsRepository> {
            PointsRepositoryImpl(
                api = get()
            )
        }

        factory<GsonConverterFactory> {
            GsonConverterFactory.create()
        }

        factory<OkHttpClient> {
            OkHttpClient.Builder()
                .build()
        }

        single<Retrofit> {
            Retrofit.Builder()
                .baseUrl("https://hr-challenge.dev.tapyou.com/")
                .client(get<OkHttpClient>())
                .addConverterFactory(get<GsonConverterFactory>())
                .build()
        }

        single<PointsApi> { providePointsApiService(get()) }
    }

    private fun providePointsApiService(retrofit: Retrofit) =
        retrofit.create(PointsApi::class.java)
}