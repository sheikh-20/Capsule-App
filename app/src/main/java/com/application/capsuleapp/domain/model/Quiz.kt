package com.application.capsuleapp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Quiz(
    val question: String? = null,
    val options: List<String?>? = null,
    val ans: String? = null
)
