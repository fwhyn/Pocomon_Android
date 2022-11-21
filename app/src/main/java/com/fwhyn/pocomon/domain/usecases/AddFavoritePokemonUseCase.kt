package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.data.repository.LocalDataRepository

class AddCaughtPokemonUseCase(private val localDataRepository: LocalDataRepository) {
    suspend fun addCaughtPokemon(pokemon : Pokemon): Boolean{
            return localDataRepository.addCaughtPokemon(pokemon)
    }
}