package com.example.android_technique_collection.domain.model.inbox

import androidx.compose.runtime.Immutable

@Immutable
data class Email(
    val id: String,
    val subject: String,
    val body: String,
    val sender: String,
    val timestamp: String,
    val recipients: List<String>,
)