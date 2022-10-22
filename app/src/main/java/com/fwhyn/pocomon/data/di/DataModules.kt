package com.fwhyn.pocomon.data.di

import com.fwhyn.pocomon.data.repository.RemoteRepository
import com.fwhyn.pocomon.data.local.RoomPokemonDatabase
import com.fwhyn.pocomon.data.repository.LocalRepository
import com.fwhyn.pocomon.domain.api.RepositoryDataInterface
import com.fwhyn.pocomon.domain.api.LocalDataInterface
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    factory<RepositoryDataInterface> { RemoteRepository() }
    factory<LocalDataInterface> { LocalRepository(RoomPokemonDatabase.getDatabase(androidApplication()).roomPokemonDao()) }
}