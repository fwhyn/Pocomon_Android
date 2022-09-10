package com.fwhyn.pocomon.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.usecases.AddCaughtPokemonUseCase
import com.fwhyn.pocomon.domain.usecases.GetIsPokemonCaughtUseCase
import com.fwhyn.pocomon.domain.usecases.RemoveCaughtPokemonUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class InfoViewModel(
    private val addCaughtPokemonUseCase: AddCaughtPokemonUseCase,
    private val removeCaughtPokemonUseCase: RemoveCaughtPokemonUseCase,
    private val getIsPokemonCaughtUseCase: GetIsPokemonCaughtUseCase
) : ViewModel(), KoinComponent {
    var caught = false

    fun addCaughtPokemon(pokemon : Pokemon){
        viewModelScope.launch {
            addCaughtPokemonUseCase.addCaughtPokemon(pokemon)
            caught = true // TODO(change to caught = the Function
        }
    }

    fun deleteCaughtPokemon(pokemon : Pokemon){
        viewModelScope.launch {
            removeCaughtPokemonUseCase.removeCaughtPokemon(pokemon)
            caught = false // TODO(change to caught = the Function
        }
    }

    fun isPokemonCaught(id : Int) : Boolean{
        return getIsPokemonCaughtUseCase.isPokemonCaught(id).also { caught = it }
    }
}