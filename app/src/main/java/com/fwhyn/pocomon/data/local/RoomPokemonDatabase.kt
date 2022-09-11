package com.fwhyn.pocomon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fwhyn.pocomon.data.utils.DataConstants.Companion.DB_NAME

@Database(entities = [RoomPokemon::class], version = 1, exportSchema = false)
abstract class RoomPokemonDatabase : RoomDatabase() {
    abstract fun roomPokemonDao() : RoomPokemonDao

    companion object{
        @Volatile
        private var INSTANCE : RoomPokemonDatabase? = null

        fun getDatabase(context: Context) : RoomPokemonDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomPokemonDatabase::class.java,
                    DB_NAME
                    // TODO(add database versioning, or can replace db when already exist)
//                ).allowMainThreadQueries().createFromAsset("$DB_NAME.db").fallbackToDestructiveMigration() // for
//                migration
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}