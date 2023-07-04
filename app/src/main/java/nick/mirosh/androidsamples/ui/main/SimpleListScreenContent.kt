package nick.mirosh.androidsamples.ui.main

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import nick.mirosh.androidsamples.getImageUrl
import nick.mirosh.androidsamples.models.Pokemon

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SimpleListScreenContent(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onDeleteItem: (Pokemon) -> Unit
) {
    var listStyle by rememberSaveable {
        mutableStateOf(
            true
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = TextStyle(color = Color.White),
                        text = "TopAppBar"
                    )
                },
                actions = {
                    IconButton(onClick = {
                        listStyle = !listStyle
                    }) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Filled.Build,
                            contentDescription = "Enable grid"
                        )
                    }
                },
            )
        },
        content = {
            val pokemonList by viewModel.pokemonList.collectAsStateWithLifecycle()
            Content(pokemonList, listStyle)
        },
    )
}

@Composable
fun Content(
    pokemonList: List<Pokemon>,
    listStyle: Boolean = true,
) {
    if (pokemonList.isNotEmpty())
        if (listStyle)
            LazyColumn {
                items(pokemonList.size) { index ->
                    PokemonCard(pokemonList[index])
                }
            }
        else
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(pokemonList.size) { index ->
                    PokemonCard(pokemonList[index], false)
                }
            }
    else
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
}

private fun LazyItemScope.items(size: Int, any: Any) {

}

@Composable
fun PokemonCard(
    pokemon: Pokemon,
    isListStyle: Boolean = true
) {
    var expanded by rememberSaveable {
        mutableStateOf(
            false
        )
    }
    val modifier = Modifier.padding(8.dp)
    val rowModifier = Modifier
        .padding(8.dp, 4.dp, 8.dp, 4.dp)
        .fillMaxWidth()

    val imageModifier = Modifier
        .height(150.dp)
        .width(200.dp)
        .clip(shape = RoundedCornerShape(8.dp))
        .padding(8.dp)

    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = ""
    )

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = colorResource(pokemon.color),
        ),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    expanded = !expanded
                }
                .padding(
                    bottom = extraPadding.coerceAtLeast(
                        0.dp
                    )
                ),
        ) {
            val (name, image) = createRefs()

            Text(
                text = pokemon.name.replaceFirstChar { it.uppercaseChar() },
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.constrainAs(name) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
            )
            AsyncImage(
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .constrainAs(
                        image
                    ) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .then(
                        if (isListStyle) Modifier
                            .fillMaxWidth()
                            .padding(16.dp) else Modifier.height(120.dp).padding(16.dp)
                    ),
                model = getImageUrl(pokemon.url),
                contentDescription = "Translated description of what the image contains"
            )
        }
    }
}


@Preview
@Composable
fun SimpleListScreenContent() {

}
