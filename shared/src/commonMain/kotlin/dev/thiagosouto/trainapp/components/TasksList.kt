package dev.thiagosouto.trainapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.thiagosouto.trainapp.features.home.TaskItemUiModel

@Composable
internal fun TasksList(list: List<TaskItemUiModel>, onItemClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(list, key = { it.id }) { item ->
            Card(
                modifier = Modifier.clickable(enabled = true, onClick = { onItemClick(item.id) })
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .heightIn(56.dp)
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = item.headline,
                            modifier = Modifier.fillMaxWidth(),
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = item.supportingText,
                            modifier = Modifier.fillMaxWidth(),
                            style = TextStyle(fontSize = 14.sp)
                        )
                    }
                }
            }
        }
    }
}
