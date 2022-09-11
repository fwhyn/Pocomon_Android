package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.api.LocalDataInterface

class AddCaughtPokemonUseCase(private val localDataInterface: LocalDataInterface) {
    suspend fun addCaughtPokemon(pokemon : Pokemon): Boolean{
            return localDataInterface.addCaughtPokemon(pokemon)
    }
}