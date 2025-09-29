package com.example.bullying_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bullying_app.databinding.ActivityReportsBinding
import com.example.bullying_app.model.RelatoResponse
import com.example.bullying_app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportsBinding
    private val adapter = ReportsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerReports.layoutManager = LinearLayoutManager(this)
        binding.recyclerReports.adapter = adapter

        RetrofitClient.api.listarRelatos().enqueue(object : Callback<List<RelatoResponse>> {
            override fun onResponse(call: Call<List<RelatoResponse>>, response: Response<List<RelatoResponse>>) {
                if (response.isSuccessful) {
                    adapter.submitList(response.body())
                } else {
                    Toast.makeText(this@ReportsActivity, "Erro ao carregar relatos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<RelatoResponse>>, t: Throwable) {
                Toast.makeText(this@ReportsActivity, "Falha: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnSendReport.setOnClickListener {
            startActivity(Intent(this, SendReportActivity::class.java))
        }
    }
}
