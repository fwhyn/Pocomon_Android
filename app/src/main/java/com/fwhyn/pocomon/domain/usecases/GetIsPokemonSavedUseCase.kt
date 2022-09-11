package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.api.RoomRepositoryInterface

class GetIsPokemonSavedUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {
    fun isPokemonSaved(id: Int): Boolean {
        return roomRepositoryInterface.isPokemonSaved(id)
    }
}