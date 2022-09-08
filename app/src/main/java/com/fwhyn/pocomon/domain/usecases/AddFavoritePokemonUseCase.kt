package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.repository.RoomRepositoryInterface

class AddFavoritePokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    suspend fun addFavoritePokemon(pokemon : Pokemon): Boolean{
//        val catch = (0..1).shuffled()[0]
//        if (catch.equals(CATCH_SUCCESS)) {
            roomRepositoryInterface.addFavoritePokemon(pokemon)
            return true
//        }
//        return false
    }
}