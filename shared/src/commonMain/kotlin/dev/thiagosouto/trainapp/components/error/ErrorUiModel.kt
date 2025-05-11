package dev.thiagosouto.trainapp.components.error

/**
 * Represents the UI model for displaying error messages.
 *
 * @property title The title of the error message.
 * @property message is the friendly message to be displayed to the user.
 */
internal data class ErrorUiModel(val title: String, val message: String)
