package com.example.bullying_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.bullying_app.model.GenericResponse
import com.example.bullying_app.model.RegisterRequest
import com.example.bullying_app.model.RelatoResponse
import com.example.bullying_app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val spinnerUserType = findViewById<Spinner>(R.id.spinnerUserType)
        val etName = findViewById<EditText>(R.id.etName)
        val etMatriculaOrCargo = findViewById<EditText>(R.id.etMatriculaOrCargo)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegisterConfirm = findViewById<Button>(R.id.btnRegisterConfirm)

        btnRegisterConfirm.setOnClickListener {
            val nome = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val senha = etPassword.text.toString().trim()
            val tipo = spinnerUserType.selectedItem.toString()
            val valorExtra = etMatriculaOrCargo.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || valorExtra.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = RegisterRequest(
                nome = nome,
                email = email,
                senha = senha,
                tipo = tipo,
                matricula = if (tipo == "Aluno") valorExtra else null,
                cargo = if (tipo != "Aluno") valorExtra else null
            )

            val call = if (tipo == "Aluno") {
                RetrofitClient.api.registerAluno(request)
            } else {
                RetrofitClient.api.registerProfessor(request)
            }

            call.enqueue(object : Callback<GenericResponse> {
                override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegisterActivity, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Erro ao cadastrar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

            RetrofitClient.api.listarTodosRelatos().enqueue(object : Callback<List<RelatoResponse>> {
                override fun onResponse(call: Call<List<RelatoResponse>>, response: Response<List<RelatoResponse>>) {
                    Log.d("TESTE_API", "Código: ${response.code()} / Corpo: ${response.body()}")
                }

                override fun onFailure(call: Call<List<RelatoResponse>>, t: Throwable) {
                    Log.e("TESTE_API", "Erro: ${t.message}", t)
                }
            })

        }
    }
}
