package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.api.RoomRepositoryInterface

class GetSinglePokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {
    fun getSinglePokemon(id: Int): Pokemon = roomRepositoryInterface.getSinglePokemon(id)
}