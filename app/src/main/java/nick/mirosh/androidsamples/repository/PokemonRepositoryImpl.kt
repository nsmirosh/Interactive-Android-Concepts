package nick.mirosh.androidsamples.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import nick.mirosh.androidsamples.PokemonApiService
import nick.mirosh.androidsamples.models.Pokemon

class PokemonRepositoryImpl(private val pokemonApi: PokemonApiService) : PokemonRepository {
    private val pokemon = MutableStateFlow<List<Pokemon>>(emptyList())

    override suspend fun getPokemon() {
        withContext(Dispatchers.IO) {
            pokemonApi.getPokemon().execute().body()?.let { pokemonResponse ->
                pokemon.value = pokemonResponse.results?.map {
                    Pokemon(it.name.orEmpty(), it.url.orEmpty())
                } ?: listOf()
            }
        }
    }
}