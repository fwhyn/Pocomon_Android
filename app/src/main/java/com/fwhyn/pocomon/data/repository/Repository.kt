package com.fwhyn.pocomon.data.repository

import com.fwhyn.pocomon.data.api.RetrofitInstance
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.model.PokemonResults
import com.fwhyn.pocomon.domain.model.PokemonTypeResults
import com.fwhyn.pocomon.domain.model.Species
import com.fwhyn.pocomon.domain.repository.RepositoryInterface

class Repository : RepositoryInterface {
    override suspend fun getPokemon(id: Int): Pokemon {
        return RetrofitInstance.api.getPokemon(id)
    }

    override suspend fun getSpecies(id: Int): Species {
        return RetrofitInstance.api.getSpecies(id)
    }

    override suspend fun getPokemonList(limit: Int): PokemonResults {
        return RetrofitInstance.api.getPokemonList(limit)
    }

    override suspend fun getPokemonTypeList(type : String) : PokemonTypeResults {
        return RetrofitInstance.api.getPokemonTypeList(type)
    }
}