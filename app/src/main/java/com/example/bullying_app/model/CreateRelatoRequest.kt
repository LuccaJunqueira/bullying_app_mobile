package com.example.bullying_app.model

data class CreateRelatoRequest(
    val fk_aluno: Int,
    val descricao: String,
    val anonimo: Boolean
)
