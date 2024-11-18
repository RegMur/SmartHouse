package com.example.smarthouse

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
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



class AddressActivity : AppCompatActivity() {

    private lateinit var addressInputLayout: TextInputLayout
    private lateinit var addressEditText: EditText
    private lateinit var saveButton: Button

    private val addressPattern =
        Regex("^г\\.\\s[А-Яа-я]+,\\sул\\.\\s[А-Яа-я]+,\\sд\\.\\s\\d+(,\\sкв\\.\\s\\d+)?$")

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
        setContentView(R.layout.activity_address)
        lifecycleScope.launch{
            //декодирование дата класса
            val city = supabase.from("user").select().decodeSingle<user>()
            Log.e("!!!1", city.address)
        }
       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.address)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        addressInputLayout = findViewById(R.id.adressInputLayout)
        addressEditText = findViewById(R.id.Address)
        saveButton = findViewById(R.id.saveBut)

        saveButton.setOnClickListener {
            val address = addressEditText.text.toString().trim()
            if (validateAddress(address)) {
                saveAddress(address)
            }
        }
    }

    private fun validateAddress(address: String): Boolean {
        return if (addressPattern.matches(address)) {
            addressInputLayout.error = null
            true
        } else {
            addressInputLayout.error = "Неверный формат адреса. Пример: г. Москва, ул. Пушкина, д. 10, кв. 5"
            false
        }
    }

    private fun saveAddress(address: String) {
        // Получение текущего пользователя (замените на ваш метод аутентификации)
        val userId = client.auth.currentSession?.user?.id
        if (userId == null) {
            Toast.makeText(this, "Пользователь не найден", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Обновление таблицы `user`
                val updateUserResponse = client.from("user")
                    .update(mapOf("home_address" to address))
                    .eq("user_id", userId)
                    .execute()

                // Обновление таблицы `home`
                val updateHomeResponse = client.from("home")
                    .update(mapOf("address" to address))
                    .eq("user_id", userId)
                    .execute()

                if (updateUserResponse.error == null && updateHomeResponse.error == null) {
                    runOnUiThread {
                        Toast.makeText(this@AddressActivity, "Адрес успешно сохранен!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@AddressActivity, MainActivity::class.java)) // Переход на главный экран
                        finish()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@AddressActivity, "Ошибка сохранения данных", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@AddressActivity, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}