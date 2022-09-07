package com.fwhyn.pocomon.data.di

import com.fwhyn.pocomon.data.repository.Repository
import com.fwhyn.pocomon.data.room.RoomPokemonDatabase
import com.fwhyn.pocomon.data.room.RoomPokemonRepository
import com.fwhyn.pocomon.domain.repository.RepositoryInterface
import com.fwhyn.pocomon.domain.repository.RoomRepositoryInterface
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    factory<RepositoryInterface> {  Repository() }
    factory<RoomRepositoryInterface> {  RoomPokemonRepository(RoomPokemonDatabase.getDatabase(androidApplication()).roomPokemonDao()) }
}