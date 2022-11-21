package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.data.repository.LocalDataRepository

class GetIsPokemonSavedUseCase(private val localDataRepository: LocalDataRepository) {
    fun isPokemonSaved(id: Int): Boolean {
        return localDataRepository.isPokemonSaved(id)
    }
}