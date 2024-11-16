package com.example.smarthouse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CreateCodeActivity : AppCompatActivity() {

    private var enteredPin = ""
    private val pinDots by lazy { listOf(findViewById<View>(R.id.dot1), findViewById<View>(R.id.dot2), findViewById<View>(R.id.dot3), findViewById<View>(R.id.dot4)) }
    private val buttons by lazy { findViewById<GridLayout>(R.id.keypad).children.filterIsInstance<Button>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_code)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_code)) { v, insets ->
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
                        savePinToPreferences(enteredPin)
                        Toast.makeText(this, "Пин-код сохранен!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, AuthCodeActivity::class.java))
                        finish()
                    }
                }
            }
        }

        private fun updatePinDots() {
            pinDots.forEachIndexed { index, view ->
                view.setBackgroundResource(if (index < enteredPin.length) R.drawable.circle_filled else R.drawable.circle_unfilled)
            }
        }

        private fun savePinToPreferences(pin: String) {
            val sharedPreferences = getSharedPreferences("PinCodePrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("PIN_CODE", pin).apply()
        }
    }