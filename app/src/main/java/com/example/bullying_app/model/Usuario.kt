package com.example.bullying_app.model

data class Usuario(
    val id: Int,              // RA_aluno ou id_professor
    val nome: String,
    val email: String,
    val tipo: String          // "ALUNO" ou "PROFESSOR"
)
