package dev.thiagosouto.trainapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
