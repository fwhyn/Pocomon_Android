package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.repository.RoomRepositoryInterface

class AddCaughtPokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    suspend fun addCaughtPokemon(pokemon : Pokemon): Boolean{
//        val catch = (0..1).shuffled()[0]
//        if (catch.equals(CATCH_SUCCESS)) {
            roomRepositoryInterface.addCaughtPokemon(pokemon)
            return true
//        }
//        return false
    }
}