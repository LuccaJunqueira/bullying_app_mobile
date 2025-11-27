package com.example.bullying_app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
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

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ReportsAdapterActivity
    private lateinit var etPesquisa: EditText

    private var userId: Int = -1
    private var userType: String? = null

    private var listaOriginal: List<RelatoResponse> = emptyList()  // lista completa
    private var listaFiltrada: MutableList<RelatoResponse> = mutableListOf() // lista filtrada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        recycler = findViewById(R.id.recyclerReports)
        etPesquisa = findViewById(R.id.etPesquisa)
        val btnSendReport = findViewById<Button>(R.id.btnSendReport)

        recycler.layoutManager = LinearLayoutManager(this)

        userId = intent.getIntExtra("userId", -1)
        userType = intent.getStringExtra("userType")

        if (userType == "PROFESSOR") {
            btnSendReport.visibility = Button.GONE
        }

        // <-- aqui: criar adapter com MutableList vazia
        adapter = ReportsAdapterActivity(mutableListOf())
        recycler.adapter = adapter

        btnSendReport.setOnClickListener {
            val intent = Intent(this, SendReportActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        configurarPesquisa()
        carregarRelatos()
    }

    override fun onResume() {
        super.onResume()
        carregarRelatos()
    }

    private fun configurarPesquisa() {
        etPesquisa.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarRelatos(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filtrarRelatos(texto: String) {
        val pesquisa = texto.lowercase()

        listaFiltrada = listaOriginal.filter { relato ->
            val autor = relato.autorNome?.lowercase() ?: ""
            val descricao = relato.descricao?.lowercase() ?: ""

            autor.contains(pesquisa) || descricao.contains(pesquisa)
        }.toMutableList()

        adapter.updateData(listaFiltrada)
    }

    private fun carregarRelatos() {
        val call = if (userType == "PROFESSOR") {
            RetrofitClient.api.listarTodosRelatos()
        } else {
            RetrofitClient.api.listarRelatosAluno(userId)
        }

        call.enqueue(object : Callback<List<RelatoResponse>> {
            override fun onResponse(
                call: Call<List<RelatoResponse>>,
                response: Response<List<RelatoResponse>>
            ) {
                if (response.isSuccessful && response.body() != null) {

                    listaOriginal = response.body()!!
                    listaFiltrada = listaOriginal.toMutableList()

                    adapter.updateData(listaFiltrada)
                } else {
                    Toast.makeText(
                        this@ReportsActivity,
                        "Nenhum relato encontrado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<RelatoResponse>>, t: Throwable) {
                Toast.makeText(this@ReportsActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
