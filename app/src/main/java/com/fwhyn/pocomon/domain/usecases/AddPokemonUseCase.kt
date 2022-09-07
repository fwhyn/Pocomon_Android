package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.repository.RoomRepositoryInterface

class AddPokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    suspend fun addPokemon(pokemon: Pokemon) {
        roomRepositoryInterface.addPokemon(pokemon)
    }
}