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

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ReportsAdapterActivity
    private var userId: Int = -1
    private var userType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        recycler = findViewById(R.id.recyclerReports)
        val btnSendReport = findViewById<Button>(R.id.btnSendReport)
        recycler.layoutManager = LinearLayoutManager(this)

        userId = intent.getIntExtra("userId", -1)
        userType = intent.getStringExtra("userType")

        if (userType == "PROFESSOR") {
            btnSendReport.visibility = Button.GONE
        }

        adapter = ReportsAdapterActivity(emptyList())
        recycler.adapter = adapter

        btnSendReport.setOnClickListener {
            val intent = Intent(this, SendReportActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        carregarRelatos()
    }

    override fun onResume() {
        super.onResume()
        carregarRelatos()
    }

    private fun carregarRelatos() {
        val call = if (userType == "PROFESSOR") {
            RetrofitClient.api.listarTodosRelatos()
        } else {
            RetrofitClient.api.listarRelatosAluno(userId)
        }

        call.enqueue(object : Callback<List<RelatoResponse>> {
            override fun onResponse(call: Call<List<RelatoResponse>>, response: Response<List<RelatoResponse>>) {
                if (response.isSuccessful && response.body() != null) {
                    adapter.updateData(response.body()!!)
                } else {
                    Toast.makeText(this@ReportsActivity, "Nenhum relato encontrado", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<RelatoResponse>>, t: Throwable) {
                Toast.makeText(this@ReportsActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}


