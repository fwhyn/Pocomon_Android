package com.fwhyn.pocomon.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.usecases.AddFavoritePokemonUseCase
import com.fwhyn.pocomon.domain.usecases.GetFavoritePokemonListUseCase
import com.fwhyn.pocomon.domain.usecases.GetIsPokemonFavoriteUseCase
import com.fwhyn.pocomon.domain.usecases.RemoveFavoritePokemonUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class FavoritesViewModel(
    private val addFavoritePokemonUseCase: AddFavoritePokemonUseCase,
    private val removeFavoritePokemonUseCase: RemoveFavoritePokemonUseCase,
    private val getFavoritePokemonListUseCase: GetFavoritePokemonListUseCase,
    private val getIsPokemonFavoriteUseCase: GetIsPokemonFavoriteUseCase
) : ViewModel(), KoinComponent {

    private val _myFavoritePokemons: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val myFavoritePokemons: LiveData<List<Pokemon>>
        get() = _myFavoritePokemons

    init {
        getFavoritePokemonList()
    }

    fun getFavoritePokemonList(){
        try{
            viewModelScope.launch {
                _myFavoritePokemons.value = getFavoritePokemonListUseCase.getFavoritePokemonList()
            }
        } catch (e : Exception) {}
    }

    fun addFavoritePokemon(pokemon : Pokemon){
        viewModelScope.launch {
            addFavoritePokemonUseCase.addFavoritePokemon(pokemon)
            getFavoritePokemonList()
        }
    }

    fun deleteFavoritePokemon(pokemon : Pokemon){
        viewModelScope.launch {
            removeFavoritePokemonUseCase.removeFavoritePokemon(pokemon)
            getFavoritePokemonList()
        }
    }

    fun isPokemonFavorite(id : Int) : Boolean{
        return getIsPokemonFavoriteUseCase.isPokemonFavorite(id)
    }
}