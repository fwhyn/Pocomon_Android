package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.data.repository.LocalDataRepository
import com.fwhyn.pocomon.domain.model.Pokemon

class RenameCaughtPokemonUseCase(private val localDataRepository: LocalDataRepository) {
    suspend fun renameCaughtPokemon(pokemon: Pokemon) {
        localDataRepository.renameCaughtPokemon(pokemon)
    }
}