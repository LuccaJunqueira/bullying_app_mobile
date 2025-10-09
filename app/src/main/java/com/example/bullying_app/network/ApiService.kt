package com.example.bullying_app.network

import com.example.bullying_app.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // Login
    @POST("/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    // Cadastro de aluno
    @POST("/cadastro/aluno")
    fun registerAluno(@Body request: RegisterRequest): Call<GenericResponse>

    // Cadastro de professor
    @POST("/cadastro/professor")
    fun registerProfessor(@Body request: RegisterRequest): Call<GenericResponse>

    // Enviar relato (somente aluno)
    @POST("/relato")
    fun criarRelato(@Body request: CreateRelatoRequest): Call<GenericResponse>

    // Relatos de um aluno específico
    @GET("/relatos/aluno/{id}")
    fun listarRelatosAluno(@Path("id") idAluno: Int): Call<List<RelatoResponse>>

    // Todos os relatos (para professores)
    @GET("/relatos")
    fun listarTodosRelatos(): Call<List<RelatoResponse>>
}
