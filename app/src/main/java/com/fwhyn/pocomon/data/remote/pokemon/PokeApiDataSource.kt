package com.fwhyn.pocomon.data.remote.pokemon

import com.fwhyn.pocomon.data.remote.RetrofitInstance
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.model.PokemonResults
import com.fwhyn.pocomon.domain.model.PokemonTypeResults
import com.fwhyn.pocomon.domain.model.Species
import com.fwhyn.pocomon.data.repository.RemoteDataRepository

class PokeApiDataSource : RemoteDataRepository {
    override suspend fun getPokemon(id: Int): Pokemon {
        return RetrofitInstance.pokeApi.getPokemon(id)
    }

    override suspend fun getSpecies(id: Int): Species {
        return RetrofitInstance.pokeApi.getSpecies(id)
    }

    override suspend fun getPokemonList(limit: Int): PokemonResults {
        return RetrofitInstance.pokeApi.getPokemonList(limit)
    }

    override suspend fun getPokemonTypeList(type : String) : PokemonTypeResults {
        return RetrofitInstance.pokeApi.getPokemonTypeList(type)
    }
}