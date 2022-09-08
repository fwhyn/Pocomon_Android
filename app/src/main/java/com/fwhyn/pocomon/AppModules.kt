package com.fwhyn.pocomon

import com.fwhyn.pocomon.ui.common.dialog.CustomDialogManager
import com.fwhyn.pocomon.ui.favorites.FavoritesViewModel
import com.fwhyn.pocomon.ui.home.HomeViewModel
import com.fwhyn.pocomon.ui.info.InfoViewModel
import com.fwhyn.pocomon.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get(), get(), get(), get()) }
    viewModel { FavoritesViewModel(get(), get(), get(), get()) }
    viewModel { InfoViewModel(get(), get(), get()) }
    viewModel { SearchViewModel(get(), get(), get(), get(), get()) }
    viewModel { CustomDialogManager() }
}