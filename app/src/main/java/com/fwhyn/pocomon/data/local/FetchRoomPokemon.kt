package com.fwhyn.pocomon.data.local

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.api.RoomRepositoryInterface

class FetchRoomPokemon(private val pokemonDao: RoomPokemonDao) : RoomRepositoryInterface {
    private val pokemonItemConverter = PokemonItemConverter()

    override suspend fun addCaughtPokemon(pokemon: Pokemon): Boolean {
        pokemonDao.addCaughtPokemon(pokemonItemConverter.pokemonToRoomPokemon(pokemon, true)
        )
        return true
    }

    override suspend fun removeCaughtPokemon(pokemon: Pokemon): Boolean {
        pokemonDao.modifyCaughtPokemon(pokemonItemConverter.pokemonToRoomPokemon(pokemon, false))

        return true
    }

    override suspend fun modifyCaughtPokemon(pokemon: Pokemon): Boolean {
        pokemonDao.modifyCaughtPokemon(pokemonItemConverter.pokemonToRoomPokemon(pokemon, true))

        return true
    }

    override suspend fun addPokemon(pokemon: Pokemon): Boolean {
        pokemonDao.addRoomPokemonItem(pokemonItemConverter.pokemonToRoomPokemon(pokemon))

        return true
    }

    override suspend fun renameCaughtPokemon(pokemon: Pokemon, name: String): Boolean {
        pokemonDao.modifyCaughtPokemon(pokemonItemConverter.pokemonToRoomPokemon(pokemon, name))

        return true
    }

    override fun getCaughtPokemonList(): List<Pokemon> {
        return pokemonDao.readCaughtItems().map {
            pokemonItemConverter.roomPokemonToPokemon(it)
        }
    }

    override fun getAllPokemonList(): List<Pokemon> {
        return pokemonDao.readCaughtItems().map {
            pokemonItemConverter.roomPokemonToPokemon(it)
        }
    }

    override fun getSinglePokemon(id: Int): Pokemon {
        return pokemonItemConverter.roomPokemonToPokemon(pokemonDao.readSingleItem(id))
    }

    override fun isPokemonCaught(id: Int): Boolean {
        return pokemonDao.isPokemonCaught(id)
    }

    override fun isPokemonSaved(id: Int): Boolean {
        return pokemonDao.isPokemonSaved(id)
    }
}