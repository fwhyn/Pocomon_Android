package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.api.LocalDataInterface

class GetSinglePokemonUseCase(private val localDataInterface: LocalDataInterface) {
    fun getSinglePokemon(id: Int): Pokemon = localDataInterface.getSinglePokemon(id)
}