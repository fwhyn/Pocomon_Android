package com.fwhyn.pocomon.data.utils

class Constants {
    companion object{
        const val BASE_URL = "https://pokeapi.co/api/v2/"
        const val POKEMONS_LOAD_LIMIT = 20
        const val TOTAL_POKEMONS = 50
        const val LAST_GROUP_LOAD_LIMIT = TOTAL_POKEMONS % POKEMONS_LOAD_LIMIT
        const val DB_NAME = "pocomon_data"
    }
}