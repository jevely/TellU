package com.jevely.tellu.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.constraint.ConstraintLayout
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.jevely.tellu.BaseActivity
import com.jevely.tellu.R
import com.jevely.tellu.util.*


class MainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var main_add: Button
    private lateinit var setting_email: TextView
    private lateinit var main_tip: TextView
    private lateinit var load_fr: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        setting_email = findViewById(R.id.setting_email)
        load_fr = findViewById(R.id.load_fr)
        main_tip = findViewById(R.id.main_tip)

        main_add = findViewById(R.id.main_add)
        findViewById<TextView>(R.id.setting_version).setText(resources.getString(R.string.main_version) + ": ${getVersion()}")

        main_add.setOnClickListener(this)

        if (ShareTool.getInstance().getBoolean(ShareTool.WALL_PAPER_SET)) {
            main_add.text = resources.getString(com.jevely.tellu.R.string.main_bt_return)
        } else {
            main_add.text = resources.getString(com.jevely.tellu.R.string.main_bt_add)
        }

        load_fr.visibility = View.GONE

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

        if (!ShareTool.getInstance().getBoolean(ShareTool.USER_TIP_1)) {
            setMainTip(R.string.main_tip_click)
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.main_add -> {
                if (ShareTool.getInstance().getBoolean(ShareTool.WALL_PAPER_SET)) {
                    //还原壁纸
                    load_fr.visibility = View.VISIBLE
                    Thread(ReturnWallPaper()).start()
                } else {
                    ShareTool.getInstance().putBoolean(ShareTool.USER_TIP_1, true)
                    startActivity(Intent(this, ContentActivity::class.java))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (ShareTool.getInstance().getBoolean(ShareTool.WALL_PAPER_SET)) {
            if (!ShareTool.getInstance().getBoolean(ShareTool.USER_TIP_2)) {
                ShareTool.getInstance().putBoolean(ShareTool.USER_TIP_2, true)
                setMainTip(R.string.main_tip_success)
            }
            main_add.text = resources.getString(com.jevely.tellu.R.string.main_bt_return)
        } else {
            main_add.text = resources.getString(com.jevely.tellu.R.string.main_bt_add)
        }
    }

    private inner class ReturnWallPaper : Runnable {
        override fun run() {
            when (ShareTool.getInstance().getString(ShareTool.SET_WALLPAPER_STATE)) {
                "1" -> {
                    com.jevely.tellu.util.setWallpaper(getWallpaperFromLocal())
                }
                "2" -> {
                    com.jevely.tellu.util.setLockWallpaper7(getWallpaperFromLocal())
                }
                "3" -> {
                    com.jevely.tellu.util.setAllWallpaper(getWallpaperFromLocal())
                }
            }

            ShareTool.getInstance().putBoolean(ShareTool.WALL_PAPER_SET, false)
            handler?.sendEmptyMessage(0)
        }
    }

    private var handler: Handler? = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            load_fr.visibility = View.GONE
            main_add.text = resources.getString(R.string.main_bt_add)
        }
    }

    fun setMainTip(contentID: Int) {
        main_tip.text = resources.getText(contentID)
    }

    override fun onDestroy() {
        super.onDestroy()
        ShareTool.destroy()
        ActivityTool.destroy()
        handler = null
    }
}
