package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.PokemonTypeResults
import com.fwhyn.pocomon.domain.api.RepositoryInterface

class GetAllPokemonOfTypeUseCase(private val repository: RepositoryInterface) {
    suspend fun getAllPokemonOfType(type: String): PokemonTypeResults = repository.getPokemonTypeList(type)
}