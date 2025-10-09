package com.example.bullying_app.model

data class LoginResponse(
    val msg: String,
    val user: Usuario?        // Retorna o usuário autenticado
)
