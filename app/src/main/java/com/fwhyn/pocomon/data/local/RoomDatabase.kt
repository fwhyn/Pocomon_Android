package com.fwhyn.pocomon.data.local

import android.content.Context
import androidx.room.Room
import com.fwhyn.pocomon.data.local.pokemon.RoomPokemonDatabase
import com.fwhyn.pocomon.data.utils.DataConstants

object RoomDatabase {
    @Volatile
    private var pokemonInstance: RoomPokemonDatabase? = null

    fun getDatabase(context: Context): RoomPokemonDatabase {
        val tempInstance = pokemonInstance
        if (tempInstance != null) {
            return tempInstance
        }
        synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                RoomPokemonDatabase::class.java,
                DataConstants.DB_NAME
                // TODO(add database versioning, or can replace db when already exist)
//                ).allowMainThreadQueries().createFromAsset("$DB_NAME.db").fallbackToDestructiveMigration() // for
//                migration
            ).allowMainThreadQueries()
                .build()
            pokemonInstance = instance
            return instance
        }
    }
}