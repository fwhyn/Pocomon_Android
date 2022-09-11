package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.api.LocalDataInterface
import com.fwhyn.pocomon.domain.model.Pokemon

class RenameCaughtPokemonUseCase(private val localDataInterface: LocalDataInterface) {
    suspend fun renameCaughtPokemon(pokemon: Pokemon, name: String) {
        localDataInterface.renameCaughtPokemon(pokemon, name)
    }
}