package com.fwhyn.pocomon.domain.api

import com.fwhyn.pocomon.domain.model.Pokemon

interface LocalDataInterface {
    suspend fun addCaughtPokemon(pokemon: Pokemon): Boolean
    suspend fun removeCaughtPokemon(id: Int): Boolean
    suspend fun addPokemon(pokemon: Pokemon): Boolean
    suspend fun modifyCaughtPokemon(pokemon: Pokemon): Boolean
    suspend fun renameCaughtPokemon(pokemon: Pokemon, name: String): Boolean
    suspend fun getCaughtPokemonList(): List<Pokemon>
    fun getAllPokemonList(): List<Pokemon>
    fun getSinglePokemon(id: Int): Pokemon
    fun isPokemonCaught(id: Int): Boolean
    fun isPokemonSaved(id: Int): Boolean
}