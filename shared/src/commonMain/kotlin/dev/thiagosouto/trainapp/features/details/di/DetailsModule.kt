package dev.thiagosouto.trainapp.features.details.di

import dev.thiagosouto.trainapp.features.details.TaskDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module {
    viewModel { TaskDetailsViewModel(get()) }
}
