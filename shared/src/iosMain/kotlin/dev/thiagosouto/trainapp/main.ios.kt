package dev.thiagosouto.trainapp

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun ComposeEntryPointWithUIViewController(): UIViewController =
    ComposeUIViewController {
        App()
    }
