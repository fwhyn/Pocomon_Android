package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.PokemonResults
import com.fwhyn.pocomon.domain.api.RepositoryInterface

class GetAllPokemonNamesUseCase(private val repository: RepositoryInterface) {
    suspend fun getAllPokemonNames(limit: Int): PokemonResults = repository.getPokemonList(limit)
}