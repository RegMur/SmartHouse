package com.example.smarthouse

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

class AddDeviceActivity : AppCompatActivity() {

    private lateinit var deviceName: EditText
    private lateinit var deviceId: EditText
    private lateinit var roomSpinner: Spinner
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_device)

        deviceName = findViewById(R.id.device_name)
        deviceId = findViewById(R.id.device_id)
        roomSpinner = findViewById(R.id.room_spinner)
        saveButton = findViewById(R.id.save_button)

        supabaseClient = createSupabaseClient(/* Ваши ключи */)

        setupRoomSpinner()
        setupDeviceTypeButtons()

        saveButton.setOnClickListener {
            saveDevice()
        }
    }

    private fun setupRoomSpinner() {
        // Загрузка списка комнат из Supabase
        lifecycleScope.launch {
            val rooms = supabase.postgrest["room"]
                .select("*")
                .execute()
            val roomNames = rooms.body?.map { it["name"] as String } ?: listOf()
            val adapter = ArrayAdapter(this@AddDeviceActivity, android.R.layout.simple_spinner_item, roomNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            roomSpinner.adapter = adapter
        }
    }

    private fun saveDevice() {
        val name = deviceName.text.toString().trim().capitalize()
        val id = deviceId.text.toString().trim()
        val selectedRoom = roomSpinner.selectedItem.toString()

        if (name.isEmpty() || id.isEmpty() || selectedRoom.isEmpty()) {
            Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val response = supabase.postgrest["device"]
                .insert(
                    mapOf(
                        "name" to name,
                        "identifier" to id,
                        "room_id" to selectedRoom,
                        "type" to selectedDeviceType
                    )
                )
                .execute()

            if (response.error != null) {
                Toast.makeText(this@AddDeviceActivity, "Ошибка: ${response.error.message}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@AddDeviceActivity, "Устройство сохранено!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}