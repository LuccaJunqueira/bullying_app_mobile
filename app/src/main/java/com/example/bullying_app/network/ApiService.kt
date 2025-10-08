package com.example.bullying_app.network

import com.example.bullying_app.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // Login de usuário
    @POST("/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    // Cadastro de usuário
    @POST("/cadastro")
    fun register(@Body request: RegisterRequest): Call<GenericResponse>

    // Enviar um novo relato
    @POST("/relato")
    fun criarRelato(@Body request: CreateRelatoRequest): Call<GenericResponse>

    // Listar todos os relatos
    @GET("/relatos")
    fun listarRelatos(): Call<List<RelatoResponse>>
}
