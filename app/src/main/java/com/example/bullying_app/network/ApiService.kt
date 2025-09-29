package com.example.bullying_app.network

import com.example.bullying_app.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/cadastro")
    fun register(@Body body: RegisterRequest): Call<GenericResponse>

    @POST("/login")
    fun login(@Body body: LoginRequest): Call<LoginResponse>

    @POST("/relato")
    fun criarRelato(@Body body: CreateRelatoRequest): Call<GenericResponse>

    @GET("/relatos")
    fun listarRelatos(): Call<List<RelatoResponse>>
}
