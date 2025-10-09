package com.example.bullying_app

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bullying_app.model.CreateRelatoRequest
import com.example.bullying_app.model.GenericResponse
import com.example.bullying_app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_report)

        val etReport = findViewById<EditText>(R.id.etReport)
        val cbAnonymous = findViewById<CheckBox>(R.id.cbAnonymous)
        val btnSend = findViewById<Button>(R.id.btnSend)

        val userId = intent.getIntExtra("userId", -1)

        btnSend.setOnClickListener {
            val descricao = etReport.text.toString().trim()
            val anonimo = cbAnonymous.isChecked

            if (descricao.isEmpty()) {
                Toast.makeText(this, "Digite o relato", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = CreateRelatoRequest(
                fk_aluno = userId,
                descricao = descricao,
                anonimo = anonimo
            )

            RetrofitClient.api.criarRelato(request).enqueue(object : Callback<GenericResponse> {
                override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@SendReportActivity, "Relato enviado com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@SendReportActivity, "Erro ao enviar relato", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                    Toast.makeText(this@SendReportActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
