package com.fwhyn.pocomon.data.room

import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.repository.RoomRepositoryInterface

class RoomPokemonRepository(private val pokemonDao: RoomPokemonDao) : RoomRepositoryInterface {

    private val pokemonItemConverter = PokemonItemConverter()

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

    override suspend fun addCaughtPokemon(pokemon: Pokemon) {
        return pokemonDao.addCaughtPokemon(
            pokemonItemConverter.pokemonToRoomPokemon(
                pokemon,
                true
            )
        )
    }

    override suspend fun removeCaughtPokemon(pokemon: Pokemon) {
        pokemonDao.removeCaughtPokemon(pokemonItemConverter.pokemonToRoomPokemon(pokemon, false))
    }

    override suspend fun addPokemon(pokemon: Pokemon) {
        return pokemonDao.addRoomPokemonItem(pokemonItemConverter.pokemonToRoomPokemon(pokemon))
    }

    override fun isPokemonCaught(id: Int): Boolean {
        return pokemonDao.isPokemonCaught(id)
    }

    override fun isPokemonSaved(id: Int): Boolean {
        return pokemonDao.isPokemonSaved(id)
    }
}