package com.jevely.tellu.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.jevely.tellu.BaseActivity
import com.jevely.tellu.R
import com.jevely.tellu.TellUApplication
import com.jevely.tellu.util.ActivityTool
import com.jevely.tellu.util.ShareTool
import com.jevely.tellu.util.saveWallpaperToLocal
import com.jevely.tellu.view.WallPaperView
import kotlinx.android.synthetic.main.activity_main.*

class WatchActivity : BaseActivity(), View.OnClickListener {

    lateinit var wallpaperview: WallPaperView
    lateinit var title_back: ImageView
    lateinit var title_yes: ImageView

    private val PERMISSION1 = "android.permission.WRITE_EXTERNAL_STORAGE"

    private lateinit var load_fr: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch)
        init()
    }

    fun init() {
        val intent = intent
        val content = intent.getStringExtra("content")

        wallpaperview = findViewById(R.id.wallpaperview)
        title_back = findViewById(R.id.title_back)
        title_yes = findViewById(R.id.title_yes)
        load_fr = findViewById(R.id.load_fr)

        wallpaperview.setContent(content)

        title_back.setOnClickListener(this)
        title_yes.setOnClickListener(this)

        load_fr.visibility = View.GONE
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.title_back -> finish()
            R.id.title_yes -> {
                requestPermission(this, PERMISSION1, 1)
            }
        }
    }

    private inner class SetWallPaper : Runnable {
        override fun run() {
            val wallpaper = wallpaperview.getWallPaper()
            val nomalWallPaper = com.jevely.tellu.util.getWallpaper()
            saveWallpaperToLocal(nomalWallPaper)
            com.jevely.tellu.util.setWallpaper(wallpaper)
            ShareTool.getInstance().putBoolean(ShareTool.WALL_PAPER_SET, true)
            handler?.sendEmptyMessage(0)
        }
    }

    override fun requestSuccess(requestCode: Int, permission: List<String>) {
        super.requestSuccess(requestCode, permission)
//        wallpaperview.setBackground()
        Thread(SetWallPaper()).start()
    }

    override fun requestError(requestCode: Int, permission: List<String>) {
        super.requestError(requestCode, permission)
    }

    private var handler: Handler? = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            load_fr.visibility = View.GONE
            ActivityTool.getInstance().finishActivity()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler = null
    }
}
