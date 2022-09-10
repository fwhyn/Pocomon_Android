package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.repository.RoomRepositoryInterface

class RemoveCaughtPokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    suspend fun removeCaughtPokemon(pokemon: Pokemon) {
        roomRepositoryInterface.removeCaughtPokemon(pokemon)
    }
}