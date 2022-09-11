package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.PokemonResults
import com.fwhyn.pocomon.domain.api.RepositoryDataInterface

class GetAllPokemonNamesUseCase(private val repository: RepositoryDataInterface) {
    suspend fun getAllPokemonNames(limit: Int): PokemonResults = repository.getPokemonList(limit)
}