package nick.mirosh.androidsamples.repository

import nick.mirosh.androidsamples.models.Pokemon

interface PokemonRepository {
    suspend fun getPokemon(): List<Pokemon>
}