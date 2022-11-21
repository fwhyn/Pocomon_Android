package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.data.repository.LocalDataRepository

class GetSinglePokemonUseCase(private val localDataRepository: LocalDataRepository) {
    fun getSinglePokemon(id: Int): Pokemon = localDataRepository.getSinglePokemon(id)
}