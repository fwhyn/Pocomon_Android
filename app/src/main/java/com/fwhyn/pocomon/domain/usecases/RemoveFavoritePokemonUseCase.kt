package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.api.LocalDataInterface

class RemoveCaughtPokemonUseCase(private val localDataInterface: LocalDataInterface) {
    suspend fun removeCaughtPokemon(id: Int) {
        localDataInterface.removeCaughtPokemon(id)
    }
}