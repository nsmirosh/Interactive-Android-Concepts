package nick.mirosh.androidsamples.responses

import nick.mirosh.androidsamples.dto.PokemonResultDto

data class PokemonResponse(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PokemonResultDto>? = null
)
