package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.api.RoomRepositoryInterface

class ModifyCaughtPokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {
    suspend fun modifyCaughtPokemon(pokemon: Pokemon) {
        roomRepositoryInterface.modifyCaughtPokemon(pokemon)
    }
}