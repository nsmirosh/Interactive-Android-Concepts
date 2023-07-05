package nick.mirosh.androidsamples.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import nick.mirosh.androidsamples.PokemonApiService
import nick.mirosh.androidsamples.repository.PokemonRepository
import nick.mirosh.androidsamples.repository.PokemonRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideRepository(
        pokemonApiService: PokemonApiService
    ): PokemonRepository {
        return PokemonRepositoryImpl(
            pokemonApiService
        )
    }
}