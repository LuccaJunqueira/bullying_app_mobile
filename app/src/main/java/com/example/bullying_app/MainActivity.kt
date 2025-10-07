package com.example.bullying_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bullying_app.model.LoginRequest
import com.example.bullying_app.model.LoginResponse
import com.example.bullying_app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // Ação do botão de login
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val senha = etPassword.text.toString().trim()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = LoginRequest(email, senha)

            RetrofitClient.api.login(request).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val user = response.body()!!.user
                        Toast.makeText(this@MainActivity, "Bem-vindo, ${user.nome}!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@MainActivity, ReportsActivity::class.java)
                        intent.putExtra("userId", user.id)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@MainActivity, "Credenciais inválidas", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Erro de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Ação do botão de cadastro
        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
