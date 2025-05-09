package com.example.android_technique_collection.domain.repository

import com.example.android_technique_collection.domain.model.inbox.Email

interface EmailRepository {
    suspend fun getEmails(): List<Email>
}