package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.api.LocalDataInterface

class GetIsPokemonSavedUseCase(private val localDataInterface: LocalDataInterface) {
    fun isPokemonSaved(id: Int): Boolean {
        return localDataInterface.isPokemonSaved(id)
    }
}