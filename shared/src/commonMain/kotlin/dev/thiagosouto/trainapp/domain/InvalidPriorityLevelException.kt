package dev.thiagosouto.trainapp.domain

/**
 * Exception thrown when an invalid priority level is encountered.
 *
 * @param message The detail message for the exception.
 */
class InvalidPriorityLevelException(message: String) : Exception(message)
