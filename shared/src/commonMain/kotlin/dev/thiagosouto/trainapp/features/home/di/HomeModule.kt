package dev.thiagosouto.trainapp.features.home.di

import dev.thiagosouto.trainapp.features.home.HomeViewModel
import dev.thiagosouto.trainapp.features.home.mapper.ErrorMapper
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { ErrorMapper() }
    viewModel { HomeViewModel(get(), get()) }
}
