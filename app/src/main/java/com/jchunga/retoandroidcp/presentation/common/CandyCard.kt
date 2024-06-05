package com.jchunga.retoandroidcp.presentation.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jchunga.retoandroidcp.domain.model.Candy

@Composable
fun CandyCard(candy: Candy, onIncrement: () -> Unit, onDecrement: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(13.dp)
            .border(1.dp, Color.Gray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = candy.name, style = MaterialTheme.typography.titleLarge)
            Text(text = candy.description, style = MaterialTheme.typography.titleMedium)
            Text(text = "\$${candy.price}", style = MaterialTheme.typography.titleLarge)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDecrement) {
                Icon(Icons.Default.Remove, contentDescription = "Decrease quantity")
            }

           Text(text = if(candy.quantity != null ) candy.quantity.toString() else "0", modifier = Modifier.padding(8.dp), style = MaterialTheme.typography.titleMedium)

            IconButton(onClick = onIncrement) {
                Icon(Icons.Default.Add, contentDescription = "Increase quantity")
            }
        }
    }
}