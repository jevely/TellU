package com.jevely.tellu.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.jevely.tellu.BaseActivity
import com.jevely.tellu.R
import com.jevely.tellu.util.*

class MainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var main_add: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        main_add = findViewById(R.id.main_add)
        findViewById<TextView>(R.id.setting_version).setText("Version: ${getVersion()}")

        main_add.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.main_add -> {
//                startActivity(Intent(this, ContentActivity::class.java))
                Thread(Thr()).start()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ShareTool.destroy()
    }

    private class Thr : Runnable {
        override fun run() {
//            com.jevely.tellu.util.setWallpaper()
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                Logger.d("设置壁纸")
//                setWallpaper7()
//                Logger.d("设置壁纸完成")
//            }
            Logger.d("设置壁纸")
            setLockWallpaper()
            Logger.d("设置壁纸完成")
        }
    }

}
