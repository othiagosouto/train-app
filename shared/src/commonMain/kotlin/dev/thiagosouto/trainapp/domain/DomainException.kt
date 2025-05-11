package dev.thiagosouto.trainapp.domain

sealed class DomainException(message: String, exception: Throwable) :
    Exception(message, exception) {
    class ServerException(code: Int, throwable: Throwable) :
        DomainException("Server failed with code: $code", throwable)

    class ClientException(code: Int, throwable: Throwable) :
        DomainException("Client failed with code: $code", throwable)

    class NetworkException(throwable: Throwable) :
        DomainException("Internet not available", throwable)

    class UnhandledException(throwable: Throwable) :
        DomainException("Unhandled exception", throwable)
}
