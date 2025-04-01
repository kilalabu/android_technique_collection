package com.example.android_technique_collection.feature.inbox

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_technique_collection.domain.model.inbox.Email

@Composable
fun EmailDetailContent(email: Email, modifier: Modifier = Modifier) {
    Column(modifier.padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
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
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
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
                Text(text = email.subject, style = MaterialTheme.typography.labelMedium)
                Row {
                    Text(
                        "To: ",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = email.recipients.joinToString(","),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.alpha(0.5f),
                    )
                }
            }
        }
        @Suppress("DEPRECATION") // Deprecated in Android but not yet available in CM
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        Text(text = email.body, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
private fun EmailDetailContentPreview() {
    EmailDetailContent(
        email = Email(
            id = "1",
            subject = "Meeting re-sched!",
            body =
                "Hey, I'm going to be out of the office tomorrow. Can we reschedule our meeting for Thursday or next week?",
            sender = "Ali Connors",
            timestamp = "3:00 PM",
            recipients = listOf("all@example.com"),
        )
    )
}