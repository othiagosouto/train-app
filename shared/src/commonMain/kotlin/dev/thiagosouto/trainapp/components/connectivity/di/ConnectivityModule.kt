package dev.thiagosouto.trainapp.components.connectivity.di

import dev.jordond.connectivity.Connectivity
import dev.thiagosouto.trainapp.components.connectivity.ConnectivityViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val connectivityStatusBarModule = module {
    single { Connectivity() }
    viewModel { ConnectivityViewModel(get(), get()) }
}
