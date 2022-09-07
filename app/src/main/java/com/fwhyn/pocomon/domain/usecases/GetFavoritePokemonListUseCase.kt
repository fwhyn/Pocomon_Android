package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.repository.RoomRepositoryInterface

class GetFavoritePokemonListUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    suspend fun getFavoritePokemonList() : List<Pokemon> = roomRepositoryInterface.getFavoritePokemonList()
}