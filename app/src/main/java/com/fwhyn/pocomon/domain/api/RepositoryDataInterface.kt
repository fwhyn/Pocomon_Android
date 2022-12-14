package com.fwhyn.pocomon.domain.api

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.model.PokemonResults
import com.fwhyn.pocomon.domain.model.PokemonTypeResults
import com.fwhyn.pocomon.domain.model.Species

interface RepositoryDataInterface {
    suspend fun getPokemon(id: Int): Pokemon
    suspend fun getSpecies(id: Int): Species
    suspend fun getPokemonList(limit: Int): PokemonResults
    suspend fun getPokemonTypeList(type : String) : PokemonTypeResults
}