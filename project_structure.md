# Estructura del Proyecto: TradingView Alert Bridge

Este proyecto es una aplicación de Android que utiliza un `NotificationListenerService` para interceptar notificaciones de la aplicación oficial de TradingView y reenviarlas a un Bot de Telegram.

## Componentes Principales

1.  **NotificationListenerService (`TradingViewService.kt`):**
    *   Monitorea las notificaciones entrantes.
    *   Filtra las notificaciones que provienen del paquete `com.tradingview.tradingviewapp`.
    *   Extrae el título y el cuerpo del mensaje.
    *   Envía los datos al Bot de Telegram.

2.  **Configuración del Bot de Telegram:**
    *   Se requiere un `BOT_TOKEN` y un `CHAT_ID`.
    *   Utiliza una solicitud HTTP POST simple a la API de Telegram.

3.  **Interfaz de Usuario (`MainActivity.kt`):**
    *   Permite al usuario ingresar el Token del Bot y el Chat ID.
    *   Muestra el estado del servicio (si tiene permisos de notificación).
    *   Botón para probar la conexión con Telegram.

4.  **Permisos (`AndroidManifest.xml`):**
    *   `android.permission.BIND_NOTIFICATION_LISTENER_SERVICE`
    *   `android.permission.INTERNET`

## Archivos a Generar

*   `app/src/main/AndroidManifest.xml`
*   `app/src/main/java/com/manus/tvbridge/MainActivity.kt`
*   `app/src/main/java/com/manus/tvbridge/TradingViewService.kt`
*   `app/src/main/java/com/manus/tvbridge/TelegramSender.kt`
*   `app/res/layout/activity_main.xml`
