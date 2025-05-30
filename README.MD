# TrainApp
A mobile application for tracking train-related tasks, built with Kotlin and Jetpack Compose for a
modern, multiplatform UI experience.

## Screenshots

### Android
| Home                             | Details                                |
|----------------------------------|----------------------------------------|
| ![Home](images/android_home.png) | ![Details](images/android_details.png) |

### iOS
| Home                         | Details                            |
|------------------------------|------------------------------------|
| ![Home](images/ios_home.png) | ![Details](images/ios_details.png) |


## Config
- Android Studio Meerkat | 2024.3.1 Patch 2

## CI
TrainApp uses GitHub Actions to automate CI/CD workflows, ensuring consistent 
builds, tests, and deployments.

## Static analysis
- Detekt(only for shared module, but could be extended to all modules)
- Swiftlint for iOS project

## Main dependencies
- Koin: Dependency injection framework for managing dependencies.
- Ktor: Handles network requests with a lightweight, asynchronous HTTP client.
- Kotlin Serialization: Provides JSON serialization and deserialization.
- Connectivity: Monitors network status and emits events on connectivity changes.
- Navigation: Kotlin-based navigation for navigating between screens.
- Compose multiplatform for UI

## Architecture
This project uses MVVM and compose multiplatform architecture.

Key components:
- ConnectivityStatusBar: Displays offline mode bar and triggers data refresh when connectivity is restored.
- TaskRepository: Exposes a SharedFlow for real-time UI updates and provides a refresh method to reload data.
- TaskCache: A singleton-scoped cache (via Koin) shared across instances for efficient cache.
- TaskService: Abstracts remote data access using the Ktor client for network operations.

### Know issues
- This project is currently using "2.1.20-2.0.1" for ksp, which is not compatible with Koin-annotations, [Latest KSP compatible version: 2.0.21-1.0.28](https://insert-koin.io/docs/setup/annotations/)
- Offline support is made through in-memory cache instead of persistent storage for sake of simplicity, however, this could be easily extended with SQLDelight or Room multiplatform.
