package com.fwhyn.pocomon

import com.fwhyn.pocomon.ui.MainViewModel
import com.fwhyn.pocomon.ui.caught.CaughtViewModel
import com.fwhyn.pocomon.ui.home.HomeViewModel
import com.fwhyn.pocomon.ui.info.InfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel { CaughtViewModel(get(), get(), get(), get()) }
    viewModel { InfoViewModel(get(), get(), get(), get(), get()) }
    viewModel { MainViewModel() }
}