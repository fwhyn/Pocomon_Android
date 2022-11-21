package com.fwhyn.pocomon.data.local.pokemon

import androidx.room.*

@Dao
interface RoomPokemonDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRoomPokemonItem(roomPokemonEntity: RoomPokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCaughtPokemon(pokemon: RoomPokemonEntity)

    @Update
    suspend fun modifyCaughtPokemon(pokemon: RoomPokemonEntity)

    @Query("DELETE FROM pokemon_list WHERE id = :id")
    suspend fun removePokemonItem(id: Int)

    @Query("SELECT * FROM pokemon_list WHERE is_caught = 1 ORDER BY id ASC")
    fun readCaughtItems(): List<RoomPokemonEntity>

    @Query("SELECT * FROM pokemon_list ORDER BY id ASC")
    fun readAllItems(): List<RoomPokemonEntity>

    @Query("SELECT * FROM pokemon_list WHERE id = :id")
    fun readSingleItem(id: Int): RoomPokemonEntity

    @Query("SELECT EXISTS(SELECT * FROM pokemon_list WHERE id = :id AND is_caught = 1)")
    fun isPokemonCaught(id: Int): Boolean

    @Query("SELECT EXISTS(SELECT * FROM pokemon_list WHERE id = :id)")
    fun isPokemonSaved(id: Int): Boolean
}