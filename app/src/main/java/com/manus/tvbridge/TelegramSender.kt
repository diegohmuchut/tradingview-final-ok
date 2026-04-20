package com.manus.tvbridge

import android.util.Log
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object TelegramSender {

    private const val TAG = "TelegramSender"

    suspend fun sendMessage(botToken: String, chatId: String, message: String): Boolean {
        val urlString = "https://api.telegram.org/bot$botToken/sendMessage"
        val jsonInputString = "{\"chat_id\": \"$chatId\", \"text\": \"$message\"}"

        return try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            OutputStreamWriter(connection.outputStream).use { it.write(jsonInputString) }

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "Mensaje enviado a Telegram con éxito.")
                true
            } else {
                val errorStream = connection.errorStream?.bufferedReader()?.readText()
                Log.e(TAG, "Error al enviar mensaje a Telegram. Código: $responseCode, Error: $errorStream")
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Excepción al enviar mensaje a Telegram: ${e.message}", e)
            false
        }
    }
}
