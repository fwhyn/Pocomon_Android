package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.data.repository.LocalDataRepository

class GetIsPokemonCaughtUseCase(private val localDataRepository: LocalDataRepository) {
    fun isPokemonCaught(id : Int) : Boolean{
        return localDataRepository.isPokemonCaught(id)
    }
}