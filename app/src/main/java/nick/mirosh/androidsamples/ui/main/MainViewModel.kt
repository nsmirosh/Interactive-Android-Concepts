package nick.mirosh.androidsamples.ui.main

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nick.mirosh.androidsamples.R
import nick.mirosh.androidsamples.models.Pokemon
import nick.mirosh.androidsamples.repository.PokemonRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ViewModel() {

    private val _pokemonList = MutableStateFlow(listOf<Pokemon>())
    val pokemonList = _pokemonList.asStateFlow()

    init {
        viewModelScope.launch {
            _pokemonList.value = pokemonRepository.getPokemon().map {
                it.copy(color = getRandomColor())
            }
        }
    }

    fun onRowClick(pokemon: Pokemon) {
    }

    private fun getRandomColor() =
        listOf(
            R.color.color1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5
        ).random()
}