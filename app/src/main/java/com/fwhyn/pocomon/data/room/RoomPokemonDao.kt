package com.fwhyn.pocomon.data.room

import androidx.room.*
import com.fwhyn.pocomon.data.room.RoomPokemon

@Dao
interface RoomPokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRoomPokemonItem(roomPokemon: RoomPokemon)

    @Query("SELECT * FROM pokemon_list WHERE is_caught = 1 ORDER BY id ASC")
    fun readCaughtItems(): List<RoomPokemon>

    @Query("SELECT * FROM pokemon_list ORDER BY id ASC")
    fun readAllItems(): List<RoomPokemon>

    @Query("SELECT * FROM pokemon_list WHERE id = :id")
    fun readSingleItem(id: Int): RoomPokemon

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCaughtPokemon(pokemon: RoomPokemon)

    @Update
    suspend fun removeCaughtPokemon(pokemon: RoomPokemon)

    @Query("SELECT EXISTS(SELECT * FROM pokemon_list WHERE id = :id AND is_caught = 1)")
    fun isPokemonCaught(id: Int): Boolean

    @Query("SELECT EXISTS(SELECT * FROM pokemon_list WHERE id = :id)")
    fun isPokemonSaved(id: Int): Boolean
}