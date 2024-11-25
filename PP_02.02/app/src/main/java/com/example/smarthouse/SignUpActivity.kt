package com.example.smarthouse

import android.R.attr.value
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
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
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
        /*lifecycleScope.launch{
            //декодирование дата класса
            val city = supabase.from("user").select().decodeSingle<user>()
            Log.e("!!!1", city.username)
        }*/

        nameEditText = findViewById(R.id.nameSU)
        emailEditText = findViewById(R.id.emailSU)
        passwordEditText = findViewById(R.id.passwordSU)
        signUpButton = findViewById(R.id.singUpBut)
        signInButton = findViewById(R.id.signInBut)

        signUpButton.setOnClickListener {
            val nameProfile = nameEditText.text.toString().trim()
            val emailProfile = emailEditText.text.toString().trim()
            val passwordProfile = passwordEditText.text.toString().trim()

            if (validateInput(nameProfile, emailProfile, passwordProfile)) {
                registerUser(nameProfile, emailProfile, passwordProfile)
            }
        }

        signInButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    private fun validateInput(nameProfile: String, emailProfile: String, passwordProfile: String): Boolean {
        var isValid = true

        if (nameProfile.isEmpty()) {
            nameInputLayout.error = "Имя не может быть пустым"
            isValid = false
        } else {
            nameInputLayout.error = null
        }

        if (emailProfile.isEmpty() || !emailPattern.matches(emailProfile)) {
            emailInputLayout.error = "Введите корректный email (пример: name@domen.ru)"
            isValid = false
        } else {
            emailInputLayout.error = null
        }

        if (passwordProfile.length < 8) {
            passwordInputLayout.error = "Пароль должен быть не менее 8 символов"
            isValid = false
        } else {
            passwordInputLayout.error = null
        }

        return isValid
    }

    private fun registerUser(nameProfile: String, emailProfile: String, passwordProfile: String) {
        lifecycleScope.launch {
            try {
                // Вход пользователя в Supabase
                val response = supabase.auth.signUpWith(Email) {
                    email = emailProfile
                    password = passwordProfile
                }
                val session = supabase.auth.currentSessionOrNull()
                val user = session?.user?.id

                if (session != null && user != null) {
                    // Если регистрация успешна, добавляем дополнительную информацию в таблицу `user`
                    supabase
                        .from("user")
                        /*.insert {
                            obj("user_id") { value = user.id }
                            obj("username") { value = nameProfile }
                            obj("profile_image") { value = null }
                            obj("address") { value = null }
                        }*/

                    Toast.makeText(this@SignUpActivity, "Регистрация успешна!", Toast.LENGTH_SHORT).show()

                    // Переход на экран создания PIN-кода
                    startActivity(Intent(this@SignUpActivity, CreateCodeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@SignUpActivity, "Ошибка регистрации", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@SignUpActivity, "Ошибка регистрации", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
