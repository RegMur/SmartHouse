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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        lifecycleScope.launch{
            //декодирование дата класса
            val city = supabase.from("user").select().decodeSingle<user>()
            Log.e("!!!1", city.username)
            Log.e("!!!2", city.profile_image.toString())
        }
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
        lifecycleScope.launch {
            try {
                // Регистрация пользователя в Supabase
                val response = client.auth.signUpWithPassword(email, password)

                if (response.user != null) {
                    // Если регистрация успешна, добавляем дополнительную информацию в таблицу `user`
                    client.postgrest["user"].insert(
                        user(
                            user_id = response.user.id,
                            username = name,
                            profile_image = null,
                            address = null
                        )
                    )

                    Toast.makeText(this@SignUpActivity, "Регистрация успешна!", Toast.LENGTH_SHORT).show()

                    // Переход на экран создания PIN-кода
                    startActivity(Intent(this@SignUpActivity, CreateCodeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@SignUpActivity, "Ошибка регистрации: ${response.error?.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@SignUpActivity, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}