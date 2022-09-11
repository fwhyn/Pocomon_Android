package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.api.LocalDataInterface

class GetCaughtPokemonListUseCase(private val localDataInterface: LocalDataInterface) {
    suspend fun getCaughtPokemonList() : List<Pokemon> = localDataInterface.getCaughtPokemonList()
}