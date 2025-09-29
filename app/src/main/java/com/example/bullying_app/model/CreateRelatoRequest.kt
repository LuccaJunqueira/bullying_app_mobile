package com.example.bullying_app.model

data class CreateRelatoRequest(
    val usuario_id: Int,
    val texto: String,
    val anonimo: Boolean
)
