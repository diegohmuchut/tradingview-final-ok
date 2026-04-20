package com.manus.tvbridge

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var botTokenEditText: EditText
    private lateinit var chatIdEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var testButton: Button
    private lateinit var statusTextView: TextView
    private lateinit var enableServiceButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        botTokenEditText = findViewById(R.id.botTokenEditText)
        chatIdEditText = findViewById(R.id.chatIdEditText)
        saveButton = findViewById(R.id.saveButton)
        testButton = findViewById(R.id.testButton)
        statusTextView = findViewById(R.id.statusTextView)
        enableServiceButton = findViewById(R.id.enableServiceButton)

        loadSettings()
        updateServiceStatus()

        saveButton.setOnClickListener { saveSettings() }
        testButton.setOnClickListener { testTelegramConnection() }
        enableServiceButton.setOnClickListener { openNotificationListenerSettings() }
    }

    override fun onResume() {
        super.onResume()
        updateServiceStatus()
    }

    private fun loadSettings() {
        val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        botTokenEditText.setText(prefs.getString("BOT_TOKEN", ""))
        chatIdEditText.setText(prefs.getString("CHAT_ID", ""))
    }

    private fun saveSettings() {
        val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        with(prefs.edit()) {
            putString("BOT_TOKEN", botTokenEditText.text.toString())
            putString("CHAT_ID", chatIdEditText.text.toString())
            apply()
        }
        Toast.makeText(this, "Configuración guardada", Toast.LENGTH_SHORT).show()
    }

    private fun updateServiceStatus() {
        if (isNotificationServiceEnabled()) {
            statusTextView.text = "Estado del Servicio: Activo"
            statusTextView.setTextColor(getColor(R.color.green))
            enableServiceButton.text = "Servicio de Notificaciones Activado"
            enableServiceButton.isEnabled = false
        } else {
            statusTextView.text = "Estado del Servicio: Inactivo (requiere activación)"
            statusTextView.setTextColor(getColor(R.color.red))
            enableServiceButton.text = "Activar Servicio de Notificaciones"
            enableServiceButton.isEnabled = true
        }
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val cn = ComponentName(this, TradingViewService::class.java)
        val flat = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return flat != null && flat.contains(cn.flattenToString())
    }

    private fun openNotificationListenerSettings() {
        startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
    }

    private fun testTelegramConnection() {
        val botToken = botTokenEditText.text.toString()
        val chatId = chatIdEditText.text.toString()

        if (botToken.isBlank() || chatId.isBlank()) {
            Toast.makeText(this, "Por favor, ingresa el Token del Bot y el Chat ID", Toast.LENGTH_LONG).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val success = TelegramSender.sendMessage(botToken, chatId, "Mensaje de prueba desde TradingView Alert Bridge.")
            withContext(Dispatchers.Main) {
                if (success) {
                    Toast.makeText(this@MainActivity, "Mensaje de prueba enviado con éxito a Telegram", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@MainActivity, "Error al enviar mensaje de prueba. Verifica el Token y Chat ID.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
