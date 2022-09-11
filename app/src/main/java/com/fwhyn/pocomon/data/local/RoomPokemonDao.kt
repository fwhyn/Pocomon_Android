package com.fwhyn.pocomon.data.local

import androidx.room.*

@Dao
interface RoomPokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRoomPokemonItem(roomPokemon: RoomPokemon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCaughtPokemon(pokemon: RoomPokemon)

    @Update
    suspend fun modifyCaughtPokemon(pokemon: RoomPokemon)

    @Query("DELETE FROM pokemon_list WHERE id = :id")
    suspend fun removePokemonItem(id: Int)

    @Query("SELECT * FROM pokemon_list WHERE is_caught = 1 ORDER BY id ASC")
    fun readCaughtItems(): List<RoomPokemon>

    @Query("SELECT * FROM pokemon_list ORDER BY id ASC")
    fun readAllItems(): List<RoomPokemon>

    @Query("SELECT * FROM pokemon_list WHERE id = :id")
    fun readSingleItem(id: Int): RoomPokemon

    @Query("SELECT EXISTS(SELECT * FROM pokemon_list WHERE id = :id AND is_caught = 1)")
    fun isPokemonCaught(id: Int): Boolean

    @Query("SELECT EXISTS(SELECT * FROM pokemon_list WHERE id = :id)")
    fun isPokemonSaved(id: Int): Boolean
}