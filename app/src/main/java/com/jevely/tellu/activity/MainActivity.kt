package com.jevely.tellu.activity

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.support.constraint.ConstraintLayout
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.jevely.tellu.BaseActivity
import com.jevely.tellu.R
import com.jevely.tellu.TellUApplication
import com.jevely.tellu.util.*


class MainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var main_add: Button
    private lateinit var setting_email: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        setting_email = findViewById(R.id.setting_email)

        main_add = findViewById(R.id.main_add)
        findViewById<TextView>(R.id.setting_version).setText(resources.getString(R.string.main_version) + ": ${getVersion()}")

        main_add.setOnClickListener(this)

        if (ShareTool.getInstance().getBoolean(ShareTool.WALL_PAPER_SET)) {
            main_add.text = resources.getString(com.jevely.tellu.R.string.main_bt_return)
        } else {
            main_add.text = resources.getString(com.jevely.tellu.R.string.main_bt_add)
        }

        setting_email.getViewTreeObserver().addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                setting_email.getViewTreeObserver().removeOnPreDrawListener(this)
                if (checkDeviceHasNavigationBar(this@MainActivity)) {
                    val navigationbarHeight = getNavigationbarHeight(this@MainActivity)

                    val params: ConstraintLayout.LayoutParams =
                        setting_email.layoutParams as ConstraintLayout.LayoutParams
                    params.bottomMargin = navigationbarHeight + dp2px(16f)

                    setting_email.layoutParams = params
                }
                return false
            }
        })

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.main_add -> {
                if (ShareTool.getInstance().getBoolean(ShareTool.WALL_PAPER_SET)) {
                    main_add.text = resources.getString(com.jevely.tellu.R.string.main_bt_add)
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
            main_add.text = resources.getString(com.jevely.tellu.R.string.main_bt_return)
        } else {
            main_add.text = resources.getString(com.jevely.tellu.R.string.main_bt_add)
        }
    }

    private inner class ReturnWallPaper : Runnable {
        override fun run() {
            com.jevely.tellu.util.setWallpaper(getWallpaperFromLocal())
            ShareTool.getInstance().putBoolean(ShareTool.WALL_PAPER_SET, false)
            Looper.prepare()
            Toast.makeText(
                TellUApplication.getContext(),
                resources.getString(com.jevely.tellu.R.string.success),
                Toast.LENGTH_SHORT
            )
                .show()
            Looper.loop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ShareTool.destroy()
        ActivityTool.destroy()
    }
}
