package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.data.repository.LocalDataRepository

class ModifyCaughtPokemonUseCase(private val localDataRepository: LocalDataRepository) {
    suspend fun modifyCaughtPokemon(pokemon: Pokemon) {
        localDataRepository.modifyCaughtPokemon(pokemon)
    }
}