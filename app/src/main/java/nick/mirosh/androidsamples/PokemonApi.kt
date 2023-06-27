package nick.mirosh.androidsamples

import nick.mirosh.androidsamples.responses.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
    fun getPokemon(
    ): Call<PokemonResponse>
}