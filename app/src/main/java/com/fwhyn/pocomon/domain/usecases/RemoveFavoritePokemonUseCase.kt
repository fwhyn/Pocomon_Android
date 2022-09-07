package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.repository.RoomRepositoryInterface

class RemoveFavoritePokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    suspend fun removeFavoritePokemon(pokemon: Pokemon) {
        roomRepositoryInterface.removeFavoritePokemon(pokemon)
    }
}