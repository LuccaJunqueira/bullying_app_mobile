package com.example.bullying_app.model

import com.google.gson.annotations.SerializedName

data class RelatoResponse(
    @SerializedName("id_relato")
    val id: Int,
    val descricao: String,
    @SerializedName("data_envio")
    val dataEnvio: String,
    val anonimo: Boolean,
    @SerializedName("autor_nome")
    val autorNome: String?
)
