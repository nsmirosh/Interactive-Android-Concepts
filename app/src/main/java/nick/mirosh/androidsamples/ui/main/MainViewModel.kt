package nick.mirosh.androidsamples.ui.main

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nick.mirosh.androidsamples.models.Pokemon
import nick.mirosh.androidsamples.repository.PokemonRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ViewModel() {

    val pokemonList = listOf<Pokemon>().toMutableStateList()

    init {
        viewModelScope.launch {
            pokemonList.clear()
            pokemonList.addAll(pokemonRepository.getPokemon())
        }
    }
    fun onRowClick(pokemon: Pokemon) {
        pokemonList.remove(pokemon)
    }
}