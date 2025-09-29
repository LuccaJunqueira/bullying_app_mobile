package com.example.bullyingapp.data.api

import com.example.bullyingapp.data.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("cadastro")
    fun register(@Body req: RegisterRequest): Call<GenericResponse>

    @POST("login")
    fun login(@Body req: LoginRequest): Call<LoginResponse>

    @POST("relato")
    fun createRelato(@Body req: CreateRelatoRequest): Call<GenericResponse>

    @GET("relatos")
    fun listRelatos(): Call<List<RelatoResponse>>
}
