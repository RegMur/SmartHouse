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
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

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
               // registerUser(nameProfile, emailProfile, passwordProfile)
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

    private fun registerUser(email_: String, password_: String) {
        lifecycleScope.launch {
            try {
                // Вход пользователя в Supabase
                supabase.auth.signUpWith(Email) {
                    email = email_
                    password = password_
                }
                val session = supabase.auth.currentSessionOrNull()
                val userid = session?.user?.id
                Log.e("USER!", userid.toString())

                /* val session = userid.auth.signUpWith(email, password)
                             if (session.user != null) {
                                  // Если регистрация успешна, добавляем дополнительную информацию в таблицу `user`
                                  userid.postgrest["user"].insert(
                                      user(
                                          user_id = response.userid.id,
                                          username = name,
                                          profile_image = null,
                                          address = null
                                      )
                                  )*/

                Toast.makeText(this@SignUpActivity, "Регистрация успешна!", Toast.LENGTH_SHORT)
                    .show()

                // Переход на экран создания PIN-кода
                startActivity(Intent(this@SignUpActivity, CreateCodeActivity::class.java))
                finish()

            } catch (e: Exception) {
                Toast.makeText(this@SignUpActivity, "Ошибка регистрации", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
