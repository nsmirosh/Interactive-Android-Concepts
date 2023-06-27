package nick.mirosh.androidsamples.repository

import kotlinx.coroutines.flow.StateFlow
import nick.mirosh.androidsamples.models.Pokemon

interface PokemonRepository {
    val pokemon: StateFlow<List<Pokemon>>
    suspend fun getPokemon()
}