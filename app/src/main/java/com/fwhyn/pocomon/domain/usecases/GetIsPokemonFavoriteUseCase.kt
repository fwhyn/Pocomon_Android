package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.api.LocalDataInterface

class GetIsPokemonCaughtUseCase(private val localDataInterface: LocalDataInterface) {
    fun isPokemonCaught(id : Int) : Boolean{
        return localDataInterface.isPokemonCaught(id)
    }
}