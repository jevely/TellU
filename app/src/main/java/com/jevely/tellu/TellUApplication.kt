package com.jevely.tellu

import android.app.Application
import android.content.Context

class TellUApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        private lateinit var context: Context

        fun getContext(): Context {
            return context
        }
    }

}