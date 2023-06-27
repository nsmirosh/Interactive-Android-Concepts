package nick.mirosh.androidsamples.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
) {
    val pokemon by viewModel.pokemon.collectAsStateWithLifecycle(listOf())

    if (pokemon.isNotEmpty()) {
        LazyColumn {
            items(pokemon.size) { index ->
                val pokemon = pokemon[index]
                Row(
                    modifier = Modifier.padding(8.dp, 4.dp, 8.dp, 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = pokemon.name,
                        lineHeight = 18.sp,
                        fontSize = 14.sp

                    )
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No saved articles",
                fontSize = 24.sp,
            )
        }
    }
}