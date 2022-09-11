package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.api.RoomRepositoryInterface

class GetCaughtPokemonListUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {
    suspend fun getCaughtPokemonList() : List<Pokemon> = roomRepositoryInterface.getCaughtPokemonList()
}