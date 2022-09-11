package com.fwhyn.pocomon.domain.di

import com.fwhyn.pocomon.domain.usecases.*
import org.koin.dsl.module

val domainModule = module {
    factory { GetPokemonUseCase(get(), get(), get()) }
    factory { AddCaughtPokemonUseCase(get()) }
    factory { RemoveCaughtPokemonUseCase(get()) }
    factory { ModifyCaughtPokemonUseCase(get()) }
    factory { RenameCaughtPokemonUseCase(get()) }
    factory { AddPokemonUseCase(get()) }
    factory { GetSinglePokemonUseCase(get()) }
    factory { GetCaughtPokemonListUseCase(get()) }
    factory { GetIsPokemonCaughtUseCase(get()) }
    factory { GetIsPokemonSavedUseCase(get()) }
    factory { GetAllPokemonNamesUseCase(get()) }
    factory { GetAllPokemonOfTypeUseCase(get()) }
    factory { GetAllLocalPokemonUseCase(get()) }
}