package com.example.brs_cout.app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

class MyApp : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }
    override fun onCreate() {
        super.onCreate()

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val darkMode = sharedPrefs.getBoolean("dark_mode", false)

        val mode = if (darkMode) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}