package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.GestionGastosApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton
import java.util.concurrent.TimeUnit
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.repository.GestionGastosRepositoryApi
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.domain.repository.GastosRepository

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(): String = "https://gestionhuacalesapi.azurewebsites.net/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("baseUrl") baseUrl: String,
        moshi: Moshi,
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideGestionGastosApi(retrofit: Retrofit): GestionGastosApi =
        retrofit.create(GestionGastosApi::class.java)


    @Provides
    @Singleton
    fun provideGastosRepository(
        impl: GestionGastosRepositoryApi
    ): GastosRepository = impl
}