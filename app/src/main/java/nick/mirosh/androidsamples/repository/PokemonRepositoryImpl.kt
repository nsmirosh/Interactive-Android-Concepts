package nick.mirosh.androidsamples.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nick.mirosh.androidsamples.PokemonApiService
import nick.mirosh.androidsamples.models.Pokemon
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(private val pokemonApi: PokemonApiService) :
    PokemonRepository {

    override suspend fun getPokemon(): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            pokemonApi.getPokemon().execute().body()?.let { pokemonResponse ->
                pokemonResponse.results?.map {
                    Pokemon(it.name.orEmpty(), it.url.orEmpty(), false)
                } ?: listOf()
            }!!
        }
    }
}