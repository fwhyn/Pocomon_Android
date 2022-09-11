package com.fwhyn.pocomon.ui.caught

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.usecases.AddCaughtPokemonUseCase
import com.fwhyn.pocomon.domain.usecases.GetCaughtPokemonListUseCase
import com.fwhyn.pocomon.domain.usecases.GetIsPokemonCaughtUseCase
import com.fwhyn.pocomon.domain.usecases.RemoveCaughtPokemonUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class CaughtViewModel(
    private val addCaughtPokemonUseCase: AddCaughtPokemonUseCase,
    private val removeCaughtPokemonUseCase: RemoveCaughtPokemonUseCase,
    private val getCaughtPokemonListUseCase: GetCaughtPokemonListUseCase,
    private val getIsPokemonCaughtUseCase: GetIsPokemonCaughtUseCase
) : ViewModel(), KoinComponent {

    private val _myCaughtPokemons: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val myCaughtPokemons: LiveData<List<Pokemon>>
        get() = _myCaughtPokemons

    init {
        getCaughtPokemonList()
    }

    fun getCaughtPokemonList(){
        try{
            viewModelScope.launch {
                _myCaughtPokemons.value = getCaughtPokemonListUseCase.getCaughtPokemonList()
            }
        } catch (e : Exception) {}
    }

    fun addCaughtPokemon(pokemon : Pokemon){
        viewModelScope.launch {
            addCaughtPokemonUseCase.addCaughtPokemon(pokemon)
            getCaughtPokemonList()
        }
    }

    fun deleteCaughtPokemon(id: Int){
        viewModelScope.launch {
            removeCaughtPokemonUseCase.removeCaughtPokemon(id)
            getCaughtPokemonList()
        }
    }

    fun isPokemonCaught(id : Int) : Boolean{
        return getIsPokemonCaughtUseCase.isPokemonCaught(id)
    }
}