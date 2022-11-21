package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.PokemonResults
import com.fwhyn.pocomon.data.repository.RemoteDataRepository

class GetAllPokemonNamesUseCase(private val repository: RemoteDataRepository) {
    suspend fun getAllPokemonNames(limit: Int): PokemonResults = repository.getPokemonList(limit)
}