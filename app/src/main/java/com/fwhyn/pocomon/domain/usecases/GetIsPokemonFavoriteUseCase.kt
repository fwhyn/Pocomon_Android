package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.repository.RoomRepositoryInterface

class GetIsPokemonCaughtUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    fun isPokemonCaught(id : Int) : Boolean{
        return roomRepositoryInterface.isPokemonCaught(id)
    }
}