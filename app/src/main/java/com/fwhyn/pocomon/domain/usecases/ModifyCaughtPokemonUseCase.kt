package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.api.LocalDataInterface

class ModifyCaughtPokemonUseCase(private val localDataInterface: LocalDataInterface) {
    suspend fun modifyCaughtPokemon(pokemon: Pokemon) {
        localDataInterface.modifyCaughtPokemon(pokemon)
    }
}