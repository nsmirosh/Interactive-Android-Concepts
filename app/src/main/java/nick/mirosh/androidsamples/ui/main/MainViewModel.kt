package nick.mirosh.androidsamples.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nick.mirosh.androidsamples.repository.PokemonRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) : ViewModel() {

    val pokemon = pokemonRepository.pokemon

    init {
        viewModelScope.launch {
            pokemonRepository.getPokemon()
        }
    }
}