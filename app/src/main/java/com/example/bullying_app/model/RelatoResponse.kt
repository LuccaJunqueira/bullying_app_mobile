package com.example.bullying_app.model

import com.google.gson.annotations.SerializedName

data class RelatoResponse(
    val id: Int,
    val texto: String,
    val anonimo: Boolean,
    @SerializedName("autor_nome")
    val autorNome: String?,
    val tipo: String?
)