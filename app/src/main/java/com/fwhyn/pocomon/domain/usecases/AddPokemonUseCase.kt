package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.data.repository.LocalDataRepository

class AddPokemonUseCase(private val localDataRepository: LocalDataRepository) {
    suspend fun addPokemon(pokemon: Pokemon) {
        localDataRepository.addPokemon(pokemon)
    }
}