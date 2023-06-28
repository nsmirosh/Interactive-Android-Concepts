package nick.mirosh.androidsamples.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import nick.mirosh.androidsamples.R
import nick.mirosh.androidsamples.models.Pokemon

@Composable
fun SimpleListScreenContent(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onRowClick: (Pokemon) -> Unit
) {
    val pokemon = remember { viewModel.pokemonList }
    if (pokemon.isNotEmpty())
        LazyColumn {
            items(pokemon.size) { index ->
                val pokemon = pokemon[index]
                PokemonCard(pokemon, onRowClick)
            }
        }
    else
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
}

@Composable
fun PokemonCard(pokemon: Pokemon, onRowClick: (Pokemon) -> Unit) {
    val modifier = Modifier.padding(8.dp)
    val rowModifier = Modifier
        .padding(8.dp, 4.dp, 8.dp, 4.dp)
        .fillMaxWidth()

    val imageModifier = Modifier
        .height(150.dp)
        .width(200.dp)
        .clip(shape = RoundedCornerShape(8.dp))
        .padding(8.dp)
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = getRandomColor()
        ),
    ) {
        Row(
            modifier = rowModifier.clickable {
                onRowClick(pokemon)
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = pokemon.name.replaceFirstChar { it.uppercaseChar() },
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Bottom)
            )
            AsyncImage(
                contentScale = ContentScale.FillBounds,
                modifier = imageModifier.padding(8.dp),
                model = getImageUrl(pokemon.url),
                contentDescription = "Translated description of what the image contains"
            )
        }
    }
}


fun getImageUrl(pokemonUrl: String): String {

    val regex = Regex("\\d+(?=/[^/]*$)")
    val matches = regex.findAll(pokemonUrl)
    val id = matches.lastOrNull()?.value
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}

@Composable
fun getRandomColor(): Color {
    val list = listOf(
        R.color.color1,
        R.color.color_2,
        R.color.color_3,
        R.color.color_4,
        R.color.color_5
    )
    return colorResource(list[(0..4).random()])
}