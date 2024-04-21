package com.example.mealtime1.DatabaseFiles

import android.content.Context
import java.util.UUID

object DeviceIdManager {

    private const val PREF_NAME = "MyAppPrefs"
    private const val DEVICE_ID_KEY = "deviceId"

    fun getDeviceId(context: Context): String {
        // Try to retrieve the device ID from SharedPreferences
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var deviceId = sharedPreferences.getString(DEVICE_ID_KEY, null)

        // If device ID doesn't exist, generate a new one and save it in SharedPreferences
        if (deviceId == null) {
            deviceId = generateDeviceId()
            sharedPreferences.edit().putString(DEVICE_ID_KEY, deviceId).apply()
        }

        return deviceId!!
    }

    fun generateDeviceId(): String {
        // Generate a unique GUID (UUID) as the device ID
        return UUID.randomUUID().toString()
    }

    fun saveDeviceId(context: Context, deviceId: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(DEVICE_ID_KEY, deviceId).apply()
    }
}
