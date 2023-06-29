package nick.mirosh.androidsamples.ui.main

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import nick.mirosh.androidsamples.getImageUrl
import nick.mirosh.androidsamples.models.Pokemon

@Composable
fun SimpleListScreenContent(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onDeleteItem: (Pokemon) -> Unit
) {
    val pokemonList by viewModel.pokemonList.collectAsStateWithLifecycle()

    if (pokemonList.isNotEmpty())
        LazyColumn {
            items(pokemonList.size) { index ->
                val pokemon = pokemonList[index]
                PokemonCard(pokemon)
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
fun PokemonCard(pokemon: Pokemon) {
    var expanded by rememberSaveable { mutableStateOf(false) }
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
                .padding(bottom = extraPadding.coerceAtLeast(0.dp)),
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
                modifier = Modifier.constrainAs(image) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
                model = getImageUrl(pokemon.url),
                contentDescription = "Translated description of what the image contains"
            )
        }
    }
}


// [ ] - Swipe to dismiss
// [ ] - Add a new item
// [ ] - Undo swipe to dismiss
// [x] - Expand an item and make sure the state holds
// [x] - Collapse an item and make sure the state holds
// [x] - Make sure the color sticks and doesn't change
// [x] - Add an animation to the expand/collapse
// [ ] - delete an item
// [ ] - add animation to deletion
// [ ] - Add a parallax header to the top
// [ ] - Add animation when an item is deleted or added back
// [ ] - implement saved state handle to survive process death for my flows
// [ ] - Use ConstraintLayout to flatten everything

// [ ] Another screen
// [ ] Grid layout
// [ ] Heteregenous grid layout
// [ ] Add a bottom and a top navigation bar


//Pagination screen

// [ ] - Pagination

//[ ] - Deep link into pokemon details screen

