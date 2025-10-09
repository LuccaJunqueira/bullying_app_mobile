package com.example.bullying_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bullying_app.model.RelatoResponse
import com.example.bullying_app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

class ReportsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        val recycler = findViewById<RecyclerView>(R.id.recyclerReports)
        val btnSendReport = findViewById<Button>(R.id.btnSendReport)
        recycler.layoutManager = LinearLayoutManager(this)

        val userId = intent.getIntExtra("userId", -1)
        val userType = intent.getStringExtra("userType")

        // 🔒 Se for PROFESSOR, esconde o botão de criar relato
        if (userType == "PROFESSOR") {
            btnSendReport.visibility = Button.GONE
        }

        // 🔘 Botão "Novo Relato" — apenas alunos o verão
        btnSendReport.setOnClickListener {
            val intent = Intent(this, SendReportActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        // 🔁 Carrega relatos conforme o tipo de usuário
        carregarRelatos(userType, userId, recycler)
    }

    private fun carregarRelatos(userType: String?, userId: Int, recycler: RecyclerView) {
        Log.d("API_DEBUG", "Carregando relatos para $userType com ID $userId")

        val call: Call<List<RelatoResponse>> = if (userType == "PROFESSOR") {
            Log.d("API_DEBUG", "Chamando endpoint de professor /relatos")
            RetrofitClient.api.listarTodosRelatos()
        } else {
            Log.d("API_DEBUG", "Chamando endpoint de aluno /relatos/aluno/$userId")
            RetrofitClient.api.listarRelatosAluno(userId)
        }

        call.enqueue(object : Callback<List<RelatoResponse>> {
            override fun onResponse(call: Call<List<RelatoResponse>>, response: Response<List<RelatoResponse>>) {
                Log.d("API_DEBUG", "Response code: ${response.code()}")
                Log.d("API_DEBUG", "Response body: ${response.body()}")

                if (response.isSuccessful && response.body() != null) {
                    val adapter = ReportsAdapterActivity(response.body()!!)
                    recycler.adapter = adapter
                } else {
                    Log.e("API_DEBUG", "Erro HTTP: ${response.code()} - ${response.errorBody()?.string()}")
                    Toast.makeText(this@ReportsActivity, "Nenhum relato encontrado", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<RelatoResponse>>, t: Throwable) {
                Log.e("API_DEBUG", "Falha Retrofit", t)
                Toast.makeText(this@ReportsActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
