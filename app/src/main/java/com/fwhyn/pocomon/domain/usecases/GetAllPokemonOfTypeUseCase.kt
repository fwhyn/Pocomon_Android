package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.PokemonTypeResults
import com.fwhyn.pocomon.domain.api.RepositoryDataInterface

class GetAllPokemonOfTypeUseCase(private val repository: RepositoryDataInterface) {
    suspend fun getAllPokemonOfType(type: String): PokemonTypeResults = repository.getPokemonTypeList(type)
}