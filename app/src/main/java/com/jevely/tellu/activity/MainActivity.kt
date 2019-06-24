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
import kotlinx.android.synthetic.main.activity_main.*

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

        if (ShareTool.getInstance().getBoolean(ShareTool.WALL_PAPER_SET)) {
            main_add.text = resources.getString(R.string.main_bt_return)
        } else {
            main_add.text = resources.getString(R.string.main_bt_add)
        }


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.main_add -> {
                if (ShareTool.getInstance().getBoolean(ShareTool.WALL_PAPER_SET)) {
                    main_add.text = resources.getString(R.string.main_bt_add)
                    //还原壁纸
                    Thread(ReturnWallPaper()).start()
                } else {
                    startActivity(Intent(this, ContentActivity::class.java))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (ShareTool.getInstance().getBoolean(ShareTool.WALL_PAPER_SET)) {
            main_add.text = resources.getString(R.string.main_bt_return)
        } else {
            main_add.text = resources.getString(R.string.main_bt_add)
        }
    }

    private class ReturnWallPaper : Runnable {
        override fun run() {
            com.jevely.tellu.util.setWallpaper(getWallpaperFromLocal())
            ShareTool.getInstance().putBoolean(ShareTool.WALL_PAPER_SET, false)
        }
    }
}
