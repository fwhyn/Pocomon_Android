package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.repository.RoomRepositoryInterface

class GetIsPokemonFavoriteUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    fun isPokemonFavorite(id : Int) : Boolean{
        return roomRepositoryInterface.isPokemonFavorite(id)
    }
}