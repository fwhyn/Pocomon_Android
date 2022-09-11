package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.api.RoomRepositoryInterface
import com.fwhyn.pocomon.domain.model.Pokemon

class RenameCaughtPokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {
    suspend fun renameCaughtPokemon(pokemon: Pokemon, name: String) {
        roomRepositoryInterface.renameCaughtPokemon(pokemon, name)
    }
}