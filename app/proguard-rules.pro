# Add project specific ProGuard rules here.
# By default, the flags set here are applied in addition to the flags
# from the Android Gradle plugin defaults.
# You can add more rules here, or at the end of the ProGuard file.

# If you want to enable ProGuard in debug builds, you can add this to your build.gradle:
# buildTypes {
#     debug {
#         minifyEnabled true
#         proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
#     }
# }

# For example, if you use GSON, you might need to add:
# -keep class com.google.gson.** { *; }

# Keep all classes and methods related to the NotificationListenerService
-keep class com.manus.tvbridge.TradingViewService { *; }
-keep class com.manus.tvbridge.TelegramSender { *; }

# Keep all classes and methods related to the MainActivity
-keep class com.manus.tvbridge.MainActivity { *; }

# Keep all classes and methods related to the UI
-keep class * extends android.app.Activity
-keep class * extends android.app.Application
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver
-keep class * extends android.content.ContentProvider
-keep class * extends android.app.backup.BackupAgentHelper
-keep class * extends android.preference.Preference
-keep class * extends android.view.View

# Keep all classes and methods related to the AndroidX libraries
-keep class androidx.** { *; }

# Keep all classes and methods related to Kotlin coroutines
-keep class kotlinx.coroutines.** { *; }

# For a more complete set of rules, see:
# https://developer.android.com/studio/build/shrink-code#keep-code
