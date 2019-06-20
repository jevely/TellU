package com.jevely.tellu.util

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.jevely.tellu.TellUApplication

class ShareTool private constructor() {

    companion object {

        private var shareTool: ShareTool? = null

        @Synchronized
        fun getInstance(): ShareTool {
            if (shareTool == null) {
                shareTool = ShareTool()
            }
            return shareTool as ShareTool
        }

        fun destroy() {
            shareTool = null
        }

    }

    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    init {
        sharedPreferences = TellUApplication.getContext().getSharedPreferences("tellu", MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun putString(key: String, content: String) {
        editor.putString(key, content)
        editor.commit()
    }

    fun getString(key: String) : String? {
        return sharedPreferences.getString(key, "")
    }

    fun putBoolean(key: String, content: Boolean) {
        editor.putBoolean(key, content)
        editor.commit()
    }

    fun getBoolean(key: String) : Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

}