package com.example.smarthouse

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class user(
    val user_id: Int,
    val username: String,
    val profile_image: Int,
    val address: String
)

class SignUpActivity : AppCompatActivity() {

    private lateinit var nameInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var signInButton: Button

    private val emailPattern = Regex("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,}$")

    val supabase = createSupabaseClient(
        supabaseUrl = "https://dvqmltvcctihmjunrjlm.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImR2cW1sdHZjY3RpaG1qdW5yamxtIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE0ODU1ODEsImV4cCI6MjA0NzA2MTU4MX0.uEgEwhiwHj-VglPp7Emz_dnlsXHaW6E_DcUfJdi9RPI"
    ) {
        install(Postgrest)
        install(Auth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        lifecycleScope.launch{
            //декодирование дата класса
            val city = supabase.from("room_types").select().decodeSingle<room_types>()
            Log.e("!!!1", city.type)
            Log.e("!!!2", city.icon.toString())
        }
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sign_up)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        nameInputLayout = findViewById(R.id.nameSUInputLayout)
        emailInputLayout = findViewById(R.id.emailSUInputLayout)
        passwordInputLayout = findViewById(R.id.passwordSUInputLayout)
        nameEditText = findViewById(R.id.nameSU)
        emailEditText = findViewById(R.id.emailSU)
        passwordEditText = findViewById(R.id.passwordSU)
        signUpButton = findViewById(R.id.singUpBut)
        signInButton = findViewById(R.id.signInBut)

        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (validateInput(name, email, password)) {
                registerUser(name, email, password)
            }
        }

        signInButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    private fun validateInput(name: String, email: String, password: String): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            nameInputLayout.error = "Имя не может быть пустым"
            isValid = false
        } else {
            nameInputLayout.error = null
        }

        if (email.isEmpty() || !emailPattern.matches(email)) {
            emailInputLayout.error = "Введите корректный email (пример: name@domen.ru)"
            isValid = false
        } else {
            emailInputLayout.error = null
        }

        if (password.length < 8) {
            passwordInputLayout.error = "Пароль должен быть не менее 8 символов"
            isValid = false
        } else {
            passwordInputLayout.error = null
        }

        return isValid
    }

    private fun registerUser(name: String, email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = client.auth.signUp(email, password)
                if (response.error == null) {
                    // Сохранение дополнительных данных пользователя в таблицу user
                    val userId = response.data?.user?.id
                    if (userId != null) {
                        val result = client.from("user").insert(
                            mapOf(
                                "user_id" to userId,
                                "username" to name
                            )
                        ).execute()

                        if (result.error == null) {
                            runOnUiThread {
                                Toast.makeText(this@SignUpActivity, "Регистрация успешна!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@SignUpActivity, CreateCodeActivity::class.java)) // Экран создания ПИН-кода
                                finish()
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(this@SignUpActivity, "Ошибка сохранения данных: ${result.error?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@SignUpActivity, "Ошибка регистрации: ${response.error.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@SignUpActivity, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}