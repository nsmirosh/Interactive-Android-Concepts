package nick.mirosh.androidsamples

fun getImageUrl(pokemonUrl: String): String {

    val regex = Regex("\\d+(?=/[^/]*$)")
    val matches = regex.findAll(pokemonUrl)
    val id = matches.lastOrNull()?.value
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}