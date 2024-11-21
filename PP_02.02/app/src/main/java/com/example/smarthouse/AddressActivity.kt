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
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddressActivity : AppCompatActivity() {

    private lateinit var addressInputLayout: TextInputLayout
    private lateinit var addressEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        val addressEditText: EditText = findViewById(R.id.Address)
        val saveButton: Button = findViewById(R.id.saveBut)

        // Обработка нажатия на кнопку "Сохранить"
        saveButton.setOnClickListener {
            val address = addressEditText.text.toString().trim()

            if (validateAddress(address)) {
                //saveAddressToSupabase(address)
            } else {
                Toast.makeText(this, "Адрес не соответствует формату!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Функция проверки адреса на соответствие шаблону
    private fun validateAddress(address: String): Boolean {
        val addressPattern = Regex("г\\.\\s[А-Яа-я\\s]+,\\sул\\.\\s[А-Яа-я\\s]+,\\sд\\.\\s\\d+,\\sкв\\.\\s\\d+")
        return address.matches(addressPattern)
    }

    // Функция сохранения адреса в базу данных Supabase
    /*private fun saveAddressToSupabase(address: String) {
        lifecycleScope.launch {
            try {
                // Получение текущего пользователя
                val userId = supabase.auth.currentSession?.user?.id
                if (userId == null) {
                    Toast.makeText(this@AddressActivity, "Пользователь не авторизован!", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                // Обновление таблицы user
                val userResponse = supabase.postgrest["user"]
                    .update(mapOf("address" to address)) // Значения для обновления
                    .eq("user_id", userId) // Фильтр по `user_id`
                    .execute()

                if (userResponse.error != null) {
                    Toast.makeText(this@AddressActivity, "Ошибка сохранения в таблицу user!", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                Toast.makeText(this@AddressActivity, "Адрес успешно сохранен!", Toast.LENGTH_SHORT).show()

                // Переход на главный экран
                val intent = Intent(this@AddressActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()

            } catch (e: Exception) {
                Toast.makeText(this@AddressActivity, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }*/
}