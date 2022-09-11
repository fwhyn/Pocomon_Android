package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.api.LocalDataInterface

class AddPokemonUseCase(private val localDataInterface: LocalDataInterface) {
    suspend fun addPokemon(pokemon: Pokemon) {
        localDataInterface.addPokemon(pokemon)
    }
}