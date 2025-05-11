package dev.thiagosouto.trainapp.components.connectivity

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ConnectivityStatusBar(viewModel: ConnectivityViewModel = koinViewModel()) {
    val state = viewModel.hasError.collectAsState()
    val message = viewModel.error.collectAsState()
    AnimatedVisibility(state.value) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(color = Color.Red)
        ) {
            Text(
                message.value,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

    }

}
