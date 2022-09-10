package com.fwhyn.pocomon.domain.repository

import com.fwhyn.pocomon.domain.model.Pokemon

interface RoomRepositoryInterface {
    fun getCaughtPokemonList(): List<Pokemon>
    fun getAllPokemonList(): List<Pokemon>
    fun getSinglePokemon(id: Int): Pokemon
    suspend fun addCaughtPokemon(pokemon: Pokemon)
    suspend fun removeCaughtPokemon(pokemon: Pokemon)
    suspend fun addPokemon(pokemon: Pokemon)
    fun isPokemonCaught(id: Int): Boolean
    fun isPokemonSaved(id: Int): Boolean
}