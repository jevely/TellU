package com.jevely.tellu.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.jevely.tellu.util.Logger
import com.jevely.tellu.R
import com.jevely.tellu.TellUApplication
import com.jevely.tellu.util.getVersion

class MainActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var main_add: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
        main_add = findViewById(R.id.main_add)
        findViewById<TextView>(R.id.setting_version).setText("Version: ${getVersion()}")
    }

    override fun onClick(v: View?) {

    }

}
