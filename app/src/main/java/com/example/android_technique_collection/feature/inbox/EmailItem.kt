package com.example.android_technique_collection.feature.inbox

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.android_technique_collection.domain.model.inbox.Email

@Composable
fun EmailItem(email: Email, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Row(
        modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Image(
            Icons.Default.Person,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Magenta)
                .padding(4.dp),
            colorFilter = ColorFilter.tint(Color.White),
            contentDescription = null,
        )
        Column {
            Row {
                Text(
                    text = email.sender,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = email.timestamp,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.alpha(0.5f),
                )
            }

            Text(text = email.subject, style = MaterialTheme.typography.labelLarge)
            Text(
                text = email.body,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.alpha(0.5f),
            )
        }
    }
}