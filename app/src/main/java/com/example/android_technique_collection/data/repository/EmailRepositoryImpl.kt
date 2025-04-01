package com.example.android_technique_collection.data.repository

import com.example.android_technique_collection.domain.model.inbox.Email
import com.example.android_technique_collection.domain.repository.EmailRepository

class EmailRepositoryImpl : EmailRepository{
    private val emails = listOf(DEMO).associateBy { it.id }

    override suspend fun getEmails(): List<Email> {
        return emails.values.toList()
    }

    fun getEmail(id: String): Email {
        return emails.getValue(id)
    }

    companion object {
        val DEMO =
            Email(
                id = "1",
                subject = "Meeting re-sched!",
                body =
                    "Hey, I'm going to be out of the office tomorrow. Can we reschedule our meeting for Thursday or next week?",
                sender = "Ali Connors",
                timestamp = "3:00 PM",
                recipients = listOf("all@example.com"),
            )
    }
}