package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.api.RoomRepositoryInterface

class AddCaughtPokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {
    suspend fun addCaughtPokemon(pokemon : Pokemon): Boolean{
            return roomRepositoryInterface.addCaughtPokemon(pokemon)
    }
}