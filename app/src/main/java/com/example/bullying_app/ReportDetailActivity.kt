package com.example.bullying_app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReportDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_detail)

        val autor = intent.getStringExtra("autor") ?: "Anônimo"
        val data = intent.getStringExtra("data") ?: "Data desconhecida"
        val descricao = intent.getStringExtra("descricao") ?: "Sem descrição"

        findViewById<TextView>(R.id.tvDetailAuthor).text = autor
        findViewById<TextView>(R.id.tvDetailDate).text = data
        findViewById<TextView>(R.id.tvDetailDescription).text = descricao
    }
}
