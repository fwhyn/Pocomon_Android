package com.fwhyn.pocomon.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.usecases.AddFavoritePokemonUseCase
import com.fwhyn.pocomon.domain.usecases.GetIsPokemonFavoriteUseCase
import com.fwhyn.pocomon.domain.usecases.RemoveFavoritePokemonUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class InfoViewModel(
    private val addFavoritePokemonUseCase: AddFavoritePokemonUseCase,
    private val removeFavoritePokemonUseCase: RemoveFavoritePokemonUseCase,
    private val getIsPokemonFavoriteUseCase: GetIsPokemonFavoriteUseCase
) : ViewModel(), KoinComponent {
    var caught = false

    fun addFavoritePokemon(pokemon : Pokemon){
        viewModelScope.launch {
            addFavoritePokemonUseCase.addFavoritePokemon(pokemon)
            caught = true // TODO(change to caught = the Function
        }
    }

    fun deleteFavoritePokemon(pokemon : Pokemon){
        viewModelScope.launch {
            removeFavoritePokemonUseCase.removeFavoritePokemon(pokemon)
            caught = false // TODO(change to caught = the Function
        }
    }

    fun isPokemonFavorite(id : Int) : Boolean{
        return getIsPokemonFavoriteUseCase.isPokemonFavorite(id).also { caught = it }
    }
}