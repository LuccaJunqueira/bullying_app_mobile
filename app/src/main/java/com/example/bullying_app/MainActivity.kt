package com.example.bullyingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bullyingapp.data.model.LoginRequest
import com.example.bullyingapp.data.repository.ApiRepository
import com.example.bullyingapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val repository = ApiRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val senha = binding.etPassword.text.toString().trim()
            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha email e senha", Toast.LENGTH_SHORT).show()
            } else {
                doLogin(email, senha)
            }
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun doLogin(email: String, senha: String) {
        val req = LoginRequest(email, senha)
        repository.login(req).enqueue(object : Callback<com.example.bullyingapp.data.model.LoginResponse> {
            override fun onResponse(
                call: Call<com.example.bullyingapp.data.model.LoginResponse>,
                response: Response<com.example.bullyingapp.data.model.LoginResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    if (body.user != null) {
                        Toast.makeText(this@MainActivity, "Login realizado", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, ReportsActivity::class.java)
                        intent.putExtra("user_id", body.user.id)
                        intent.putExtra("user_name", body.user.nome)
                        intent.putExtra("user_type", body.user.tipo)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@MainActivity, "Usuário não encontrado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // pode ser 401 ou outro erro
                    Toast.makeText(this@MainActivity, "Credenciais inválidas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<com.example.bullyingapp.data.model.LoginResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
