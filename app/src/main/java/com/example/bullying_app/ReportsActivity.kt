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

class ReportsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        val recycler = findViewById<RecyclerView>(R.id.recyclerReports)
        val btnSendReport = findViewById<Button>(R.id.btnSendReport)
        recycler.layoutManager = LinearLayoutManager(this)

        // Botão para nova denúncia
        btnSendReport.setOnClickListener {
            val userId = intent.getIntExtra("userId", -1)
            val intent = Intent(this, SendReportActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        // Chamada Retrofit
        RetrofitClient.api.listarRelatos().enqueue(object : Callback<List<RelatoResponse>> {
            override fun onResponse(call: Call<List<RelatoResponse>>, response: Response<List<RelatoResponse>>) {
                if (response.isSuccessful && response.body() != null) {
                    val adapter = ReportsAdapterActivity(response.body()!!)
                    recycler.adapter = adapter
                } else {
                    Toast.makeText(this@ReportsActivity, "Erro ao carregar relatos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<RelatoResponse>>, t: Throwable) {
                Toast.makeText(this@ReportsActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
