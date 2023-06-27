package nick.mirosh.androidsamples.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import nick.mirosh.androidsamples.BuildConfig
import nick.mirosh.androidsamples.PokemonApiService
import nick.mirosh.androidsamples.networking.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
class RetrofitModule {

    @Provides
    fun providePokemonService(
        okHttpClient: OkHttpClient
    ): PokemonApiService {

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(PokemonApiService::class.java)
    }

    @Provides
    fun provideOkHttpWithLogger(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(HeaderInterceptor())
            .build()
    }
}