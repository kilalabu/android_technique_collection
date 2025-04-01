package com.example.android_technique_collection.feature.inbox

import com.slack.circuit.sharedelements.SharedTransitionKey

data class EmailSharedTransitionKey(val id: String, val type: ElementType) : SharedTransitionKey {
    enum class ElementType {
        SenderImage,
        SenderName,
        Subject,
        Body,
    }
}