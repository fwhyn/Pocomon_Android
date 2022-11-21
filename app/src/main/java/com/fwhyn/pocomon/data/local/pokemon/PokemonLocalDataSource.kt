package com.fwhyn.pocomon.data.local.pokemon

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.data.repository.LocalDataRepository

class PokemonLocalDataSource(private val roomPokemonDao: RoomPokemonDao) : LocalDataRepository {
    private val pokemonItemConverter = PokemonItemConverter()

    override suspend fun addCaughtPokemon(pokemon: Pokemon): Boolean {
        pokemon.caught = true
        roomPokemonDao.addCaughtPokemon(pokemonItemConverter.pokemonToRoomPokemon(pokemon))
        return true
    }

    override suspend fun removeCaughtPokemon(id: Int): Boolean {
        roomPokemonDao.removePokemonItem(id)

        return true
    }

    override suspend fun modifyCaughtPokemon(pokemon: Pokemon): Boolean {
        roomPokemonDao.modifyCaughtPokemon(pokemonItemConverter.pokemonToRoomPokemon(pokemon))

        return true
    }

    override suspend fun addPokemon(pokemon: Pokemon): Boolean {
        roomPokemonDao.addRoomPokemonItem(pokemonItemConverter.pokemonToRoomPokemon(pokemon))

        return true
    }

    override suspend fun renameCaughtPokemon(pokemon: Pokemon): Boolean {
        roomPokemonDao.modifyCaughtPokemon(pokemonItemConverter.pokemonToRoomPokemon(pokemon))

        return true
    }

    override suspend fun getCaughtPokemonList(): List<Pokemon> {
        return roomPokemonDao.readCaughtItems().map {
            pokemonItemConverter.roomPokemonToPokemon(it)
        }
    }

    override fun getAllPokemonList(): List<Pokemon> {
        return roomPokemonDao.readAllItems().map {
            pokemonItemConverter.roomPokemonToPokemon(it)
        }
    }

    override fun getSinglePokemon(id: Int): Pokemon {
        return pokemonItemConverter.roomPokemonToPokemon(roomPokemonDao.readSingleItem(id))
    }

    override fun isPokemonCaught(id: Int): Boolean {
        return roomPokemonDao.isPokemonCaught(id)
    }

    override fun isPokemonSaved(id: Int): Boolean {
        return roomPokemonDao.isPokemonSaved(id)
    }
}