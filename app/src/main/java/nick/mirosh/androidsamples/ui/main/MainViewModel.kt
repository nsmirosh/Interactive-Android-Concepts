package nick.mirosh.androidsamples.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nick.mirosh.androidsamples.repository.PokemonRepository

class MainViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    val pokemon = pokemonRepository.pokemon

    init {
        viewModelScope.launch {
            pokemonRepository.getPokemon()
        }
    }
}