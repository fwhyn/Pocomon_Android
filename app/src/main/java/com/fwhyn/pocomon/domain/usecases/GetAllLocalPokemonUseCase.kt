package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.api.LocalDataInterface
import com.fwhyn.pocomon.domain.model.Pokemon

class GetAllLocalPokemonUseCase(private val localDataInterface: LocalDataInterface) {
    suspend fun getAllLocalPokemon(): List<Pokemon> {
        return localDataInterface.getAllPokemonList()
    }
}