package com.fwhyn.pocomon.domain.api

import com.fwhyn.pocomon.domain.model.Pokemon

interface RoomRepositoryInterface {
    suspend fun addCaughtPokemon(pokemon: Pokemon): Boolean
    suspend fun removeCaughtPokemon(pokemon: Pokemon): Boolean
    suspend fun addPokemon(pokemon: Pokemon): Boolean
    suspend fun modifyCaughtPokemon(pokemon: Pokemon): Boolean
    suspend fun renameCaughtPokemon(pokemon: Pokemon, name: String): Boolean
    fun getCaughtPokemonList(): List<Pokemon>
    fun getAllPokemonList(): List<Pokemon>
    fun getSinglePokemon(id: Int): Pokemon
    fun isPokemonCaught(id: Int): Boolean
    fun isPokemonSaved(id: Int): Boolean
}