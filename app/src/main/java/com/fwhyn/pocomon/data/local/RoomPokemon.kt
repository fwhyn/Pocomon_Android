package com.fwhyn.pocomon.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fwhyn.pocomon.data.utils.DataConstants.Companion.DEFAULT_CAPTURE_RATE

@Entity(tableName = "pokemon_list")
data class RoomPokemon(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val base_experience: Int,
    val height: Int,
    var name: String,
    val sprites: String,
    val stats: String,
    val types: String,
    val weight: Int,
    var dominant_color: Int?,
    val genera: String,
    val description: String,
    var capture_rate: Int = DEFAULT_CAPTURE_RATE,
    var is_caught: Boolean = false,
    var custom_name: String
)