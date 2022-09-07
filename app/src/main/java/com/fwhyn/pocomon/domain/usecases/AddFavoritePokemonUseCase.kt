package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.repository.RoomRepositoryInterface

class AddFavoritePokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    suspend fun addFavoritePokemon(pokemon : Pokemon){
        roomRepositoryInterface.addFavoritePokemon(pokemon)
    }
}