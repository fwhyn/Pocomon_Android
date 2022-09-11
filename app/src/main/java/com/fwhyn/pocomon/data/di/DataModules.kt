package com.fwhyn.pocomon.data.di

import com.fwhyn.pocomon.data.repository.FetchRepository
import com.fwhyn.pocomon.data.local.RoomPokemonDatabase
import com.fwhyn.pocomon.data.local.FetchRoomPokemon
import com.fwhyn.pocomon.domain.api.RepositoryInterface
import com.fwhyn.pocomon.domain.api.RoomRepositoryInterface
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    factory<RepositoryInterface> { FetchRepository() }
    factory<RoomRepositoryInterface> { FetchRoomPokemon(RoomPokemonDatabase.getDatabase(androidApplication()).roomPokemonDao()) }
}