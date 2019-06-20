package com.jevely.tellu.util

import android.util.Log

object Logger {

    private val shouldLog = true
    private val TAG = "LJW"

    fun d(content: String) {
        if (shouldLog) {
            Log.d(TAG, content)
        }
    }

    fun e(content: String) {
        if (shouldLog) {
            Log.e(TAG, content)
        }
    }

}