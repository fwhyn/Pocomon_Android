package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.data.repository.LocalDataRepository
import com.fwhyn.pocomon.domain.model.Pokemon

class GetAllLocalPokemonUseCase(private val localDataRepository: LocalDataRepository) {
    suspend fun getAllLocalPokemon(): List<Pokemon> {
        return localDataRepository.getAllPokemonList()
    }
}