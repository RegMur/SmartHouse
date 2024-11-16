package com.example.smarthouse

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AuthCodeActivity : AppCompatActivity() {

    private var enteredPin = ""
    private val pinDots by lazy { listOf(findViewById<View>(R.id.dot1), findViewById<View>(R.id.dot2), findViewById<View>(R.id.dot3), findViewById<View>(R.id.dot4)) }
    private val buttons by lazy { findViewById<GridLayout>(R.id.keypad).children.filterIsInstance<Button>() }
    private val exitButton by lazy { findViewById<Button>(R.id.exitBut) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth_code)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.auth_code)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets*/

            buttons.forEach { button ->
                button.setOnClickListener {
                    val number = button.text.toString()
                    if (enteredPin.length < 4) {
                        enteredPin += number
                        updatePinDots()
                    }

                    if (enteredPin.length == 4) {
                        if (validatePin(enteredPin)) {
                            Toast.makeText(this, "Вход выполнен успешно!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, HomeActivity::class.java))
                        } else {
                            Toast.makeText(this, "Неверный пин-код", Toast.LENGTH_SHORT).show()
                            resetPinInput()
                        }
                    }
                }
            }

            exitButton.setOnClickListener {
                clearUserData()
                Toast.makeText(this, "Данные очищены. Возврат на экран создания кода.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, CreateCodeActivity::class.java))
                finish()
            }
        }

        private fun updatePinDots() {
            pinDots.forEachIndexed { index, view ->
                view.setBackgroundResource(if (index < enteredPin.length) R.drawable.circle_filled else R.drawable.circle_unfilled)
            }
        }

        private fun validatePin(pin: String): Boolean {
            val sharedPreferences = getSharedPreferences("PinCodePrefs", Context.MODE_PRIVATE)
            val savedPin = sharedPreferences.getString("PIN_CODE", "")
            return savedPin == pin
        }

        private fun resetPinInput() {
            enteredPin = ""
            updatePinDots()
        }

        private fun clearUserData() {
            val sharedPreferences = getSharedPreferences("PinCodePrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()
        }
    }