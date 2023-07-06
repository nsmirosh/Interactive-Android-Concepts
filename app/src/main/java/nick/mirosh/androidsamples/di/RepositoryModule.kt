package nick.mirosh.androidsamples.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import nick.mirosh.androidsamples.PokemonApiService
import nick.mirosh.androidsamples.database.TodoDao
import nick.mirosh.androidsamples.repository.PokemonRepository
import nick.mirosh.androidsamples.repository.PokemonRepositoryImpl
import nick.mirosh.androidsamples.repository.TodoRepository
import nick.mirosh.androidsamples.repository.TodoRepositoryImpl

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

    @Provides
    fun provideTodoRepository(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        todoDao: TodoDao
    ): TodoRepository {
        return TodoRepositoryImpl(
            dispatcher,
            todoDao
        )
    }
}