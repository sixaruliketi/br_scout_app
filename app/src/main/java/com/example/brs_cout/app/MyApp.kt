package com.example.brs_cout.app

import android.app.Application
import android.content.Context

class MyApp : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }
}