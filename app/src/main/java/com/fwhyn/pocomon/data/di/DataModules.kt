package com.fwhyn.pocomon.data.di

import com.fwhyn.pocomon.data.remote.pokemon.PokeApiDataSource
import com.fwhyn.pocomon.data.local.pokemon.RoomPokemonDatabase
import com.fwhyn.pocomon.data.local.pokemon.PokemonLocalDataSource
import com.fwhyn.pocomon.data.repository.RemoteDataRepository
import com.fwhyn.pocomon.data.repository.LocalDataRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    factory<RemoteDataRepository> { PokeApiDataSource() }
    factory<LocalDataRepository> { PokemonLocalDataSource(RoomPokemonDatabase.getDatabase(androidApplication()).roomPokemonDao()) }
}