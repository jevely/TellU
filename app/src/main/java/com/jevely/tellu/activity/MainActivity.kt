package com.jevely.tellu.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.jevely.tellu.BaseActivity
import com.jevely.tellu.R
import com.jevely.tellu.util.Logger
import com.jevely.tellu.util.ShareTool
import com.jevely.tellu.util.getVersion

class MainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var main_add: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        ShareTool.getInstance().getString("ljw")?.let { Logger.d(it) }
    }

    private fun init() {
        main_add = findViewById(R.id.main_add)
        findViewById<TextView>(R.id.setting_version).setText("Version: ${getVersion()}")

        main_add.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.main_add -> startActivity(Intent(this, ContentActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ShareTool.destroy()
    }

}
