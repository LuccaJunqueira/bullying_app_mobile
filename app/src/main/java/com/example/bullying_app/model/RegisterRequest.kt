package com.example.bullying_app.model

data class RegisterRequest(
    val nome: String,
    val email: String,
    val senha: String,
    val tipo: String,             // "ALUNO" ou "PROFESSOR"
    val matricula: String?,       // usado apenas se tipo == "ALUNO"
    val cargo: String?            // usado apenas se tipo == "PROFESSOR"
)
