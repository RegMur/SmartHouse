package com.example.smarthouse

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
//import io.github.jan.supabase.gotrue.auth.signInWith
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        lifecycleScope.launch{
            //декодирование дата класса
            val city = supabase.from("user").select().decodeSingle<user>()
            Log.e("!!!1", city.address)
        }

        /*emailInputLayout = findViewById(R.id.emailSIInputLayout)
        passwordInputLayout = findViewById(R.id.passwordSIInputLayout)*/
        emailEditText = findViewById(R.id.emailSI)
        passwordEditText = findViewById(R.id.passwordSI)
        signInButton = findViewById(R.id.signInBut2)
        signUpButton = findViewById(R.id.signUnBut2)

        // Обработчик кнопки "Войти"
        signInButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (validateInputs(email, password)) {
                signIn(email, password)
            }
        }

        // Обработчик кнопки "Регистрация"
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        val emailPattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}"

        if (email.isEmpty()) {
            showToast("Введите электронную почту")
            return false
        }
        if (!email.matches(emailPattern.toRegex())) {
            showToast("Некорректный формат электронной почты")
            return false
        }
        if (password.isEmpty()) {
            showToast("Введите пароль")
            return false
        }
        if (password.length < 8) {
            showToast("Пароль должен содержать минимум 8 символов")
            return false
        }
        return true
    }

    private fun signIn(email_: String, password_: String) {
        lifecycleScope.launch {
            try {
                // Вход пользователя в Supabase
                supabase.auth.signInWith(Email) {
                    email = email_
                    password = password_
                }
                val session = supabase.auth.currentSessionOrNull()
                val userid = session?.user?.id
                Log.e("USER!", userid.toString())
                showToast("Успешная авторизация")
                navigateToPinCodeScreen()
            } catch (e: Exception) {
                showToast("Ошибка авторизации: ${e.localizedMessage}")
            }
        }
    }

    private fun navigateToPinCodeScreen() {
        val intent = Intent(this, AuthCodeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}