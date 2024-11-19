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
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {

    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button

    private val emailPattern = Regex("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,}$")

/*    val supabaseUrl = "https://dvqmltvcctihmjunrjlm.supabase.co"
    val supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImR2cW1sdHZjY3RpaG1qdW5yamxtIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE0ODU1ODEsImV4cCI6MjA0NzA2MTU4MX0.uEgEwhiwHj-VglPp7Emz_dnlsXHaW6E_DcUfJdi9RPI"

    private val client = SupabaseClient(SupabaseConfig(supabaseUrl, supabaseKey))*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        lifecycleScope.launch{
            //декодирование дата класса
            val city = supabase.from("room_types").select().decodeSingle<room_types>()
            Log.e("!!!1", city.type)
            Log.e("!!!2", city.icon.toString())
        }
        /*        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sign_in)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        emailInputLayout = findViewById(R.id.emailSIInputLayout)
        passwordInputLayout = findViewById(R.id.passwordSIInputLayout)
        emailEditText = findViewById(R.id.emailSI)
        passwordEditText = findViewById(R.id.passwordSI)
        signInButton = findViewById(R.id.signInBut2)
        signUpButton = findViewById(R.id.signUnBut2)

        signInButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (validateInput(email, password)) {
                loginUser(email, password)
            }
        }

        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        var isValid = true

        if (email.isEmpty() || !emailPattern.matches(email)) {
            emailInputLayout.error = "Введите корректный email (пример: name@domen.ru)"
            isValid = false
        } else {
            emailInputLayout.error = null
        }

        if (password.isEmpty()) {
            passwordInputLayout.error = "Введите пароль"
            isValid = false
        } else {
            passwordInputLayout.error = null
        }

        return isValid
    }

    private fun loginUser(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = user_id.auth.signInWithPassword(email, password)
                if (response.error == null) {
                    runOnUiThread {
                        Toast.makeText(this@SignInActivity, "Авторизация успешна!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignInActivity, CreateCodeActivity::class.java))
                        finish()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@SignInActivity, "Ошибка: ${response.error.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@SignInActivity, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
}