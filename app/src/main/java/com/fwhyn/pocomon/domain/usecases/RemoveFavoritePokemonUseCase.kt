package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.data.repository.LocalDataRepository

class RemoveCaughtPokemonUseCase(private val localDataRepository: LocalDataRepository) {
    suspend fun removeCaughtPokemon(id: Int) {
        localDataRepository.removeCaughtPokemon(id)
    }
}