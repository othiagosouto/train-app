# Kmp template
This project serves as a boilerplate for building Kotlin Multiplatform (KMP) applications. 
It provides a solid foundation with essential tools and libraries configured for cross-platform development.

## CI
- GitHub Actions is used for CI/CD workflows.
- This project is compatible with github actions, and the goal is to have it

## Static analysis
- Detekt

## Dependencies
- Koin: For dependency injection
- Ktor: For network requests
- Kotlin serialization: For serializing/deserializing json

### Know issues
- This project is currently using "2.1.20-2.0.1" for ksp, which is not compatible with Koin-annotations, [Latest KSP compatible version: 2.0.21-1.0.28](https://insert-koin.io/docs/setup/annotations/)
