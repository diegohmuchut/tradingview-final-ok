package com.manus.tvbridge

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TradingViewService : NotificationListenerService() {

    private val TAG = "TradingViewService"

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        sbn?.let {
            // Filtrar solo notificaciones de la app de TradingView
            if (it.packageName == "com.tradingview.tradingviewapp") {
                val extras = it.notification.extras
                val title = extras.getString("android.title")
                val text = extras.getCharSequence("android.text")?.toString()

                val message = "TradingView Alert: \nTítulo: $title\nMensaje: $text"
                Log.d(TAG, "Notificación de TradingView capturada: $message")

                // Obtener el token del bot y el chat ID de las preferencias compartidas
                val prefs = applicationContext.getSharedPreferences("AppPrefs", MODE_PRIVATE)
                val botToken = prefs.getString("BOT_TOKEN", "")
                val chatId = prefs.getString("CHAT_ID", "")

                if (!botToken.isNullOrBlank() && !chatId.isNullOrBlank()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        TelegramSender.sendMessage(botToken, chatId, message)
                    }
                } else {
                    Log.e(TAG, "BOT_TOKEN o CHAT_ID no configurados. No se pudo enviar a Telegram.")
                }
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        // Opcional: Manejar la eliminación de notificaciones si es necesario
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Servicio iniciado")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Servicio destruido")
    }
}
