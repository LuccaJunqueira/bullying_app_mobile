package com.example.bullyingapp.repository

import com.example.bullyingapp.api.RetrofitClient
import com.example.bullyingapp.model.*
import retrofit2.Call

class ApiRepository {
    private val api = RetrofitClient.api

    fun register(req: RegisterRequest): Call<GenericResponse> = api.register(req)
    fun login(req: LoginRequest): Call<LoginResponse> = api.login(req)
    fun createRelato(req: CreateRelatoRequest): Call<GenericResponse> = api.createRelato(req)
    fun listRelatos(): Call<List<RelatoResponse>> = api.listRelatos()
}
