package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.data.repository.LocalDataRepository

class GetCaughtPokemonListUseCase(private val localDataRepository: LocalDataRepository) {
    suspend fun getCaughtPokemonList() : List<Pokemon> = localDataRepository.getCaughtPokemonList()
}