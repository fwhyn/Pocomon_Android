package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.PokemonTypeResults
import com.fwhyn.pocomon.data.repository.RemoteDataRepository

class GetAllPokemonOfTypeUseCase(private val repository: RemoteDataRepository) {
    suspend fun getAllPokemonOfType(type: String): PokemonTypeResults = repository.getPokemonTypeList(type)
}