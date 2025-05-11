package dev.thiagosouto.trainapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.thiagosouto.trainapp.components.connectivity.di.connectivityStatusBarModule
import dev.thiagosouto.trainapp.data.di.dataModule
import dev.thiagosouto.trainapp.data.di.urlModule
import dev.thiagosouto.trainapp.features.home.di.homeModule
import dev.thiagosouto.trainapp.features.home.view.HomeScreen
import kotlinx.serialization.Serializable
import org.koin.compose.KoinApplication

@Serializable
object HomeRoute

@Composable
fun App() {
    val navController = rememberNavController()

    KoinApplication(
        application = { modules(urlModule, dataModule, homeModule, connectivityStatusBarModule) }) {
        NavHost(navController = navController, startDestination = HomeRoute) {
            composable<HomeRoute> { HomeScreen() }
        }
    }
}
