package com.example.bullying_app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bullying_app.databinding.ActivitySendReportBinding
import com.example.bullying_app.model.CreateRelatoRequest
import com.example.bullying_app.model.GenericResponse
import com.example.bullying_app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySendReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            val texto = binding.etReport.text.toString()
            val anonimo = binding.cbAnonymous.isChecked

            if (texto.isEmpty()) {
                Toast.makeText(this, "Escreva o relato", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = CreateRelatoRequest(usuario_id = 1, texto = texto, anonimo = anonimo)

            RetrofitClient.api.criarRelato(request).enqueue(object : Callback<GenericResponse> {
                override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@SendReportActivity, response.body()?.msg ?: "Relato enviado!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@SendReportActivity, "Erro ao enviar relato", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                    Toast.makeText(this@SendReportActivity, "Falha: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
