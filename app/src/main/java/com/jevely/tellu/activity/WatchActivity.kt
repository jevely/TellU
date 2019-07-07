package com.jevely.tellu.activity

import android.opengl.Visibility
import android.os.*
import android.view.View
import android.widget.*
import com.jevely.tellu.BaseActivity
import com.jevely.tellu.R
import com.jevely.tellu.TellUApplication
import com.jevely.tellu.util.ActivityTool
import com.jevely.tellu.util.ShareTool
import com.jevely.tellu.util.saveWallpaperToLocal
import com.jevely.tellu.view.WallPaperView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_watch.*

class WatchActivity : BaseActivity(), View.OnClickListener {

    lateinit var wallpaperview: WallPaperView
    lateinit var title_back: ImageView
    lateinit var title_yes: ImageView
    lateinit var wallpaper_yes_ll: LinearLayout

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
        wallpaper_yes_ll = findViewById(R.id.wallpaper_yes_ll)

        wallpaperview.setContent(content)

        title_back.setOnClickListener(this)
        title_yes.setOnClickListener(this)

        findViewById<Button>(R.id.wallpaper_lunch).setOnClickListener(this)
        findViewById<Button>(R.id.wallpaper_lock).setOnClickListener(this)
        findViewById<Button>(R.id.wallpaper_all).setOnClickListener(this)

        load_fr.visibility = View.GONE

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.title_back -> finish()
            R.id.title_yes -> {
                requestPermission(this, PERMISSION1, 1)
            }
            R.id.wallpaper_lunch -> {
                //桌面壁纸
                wallpaper_yes_ll.visibility = View.GONE
                Thread(SetLunchWallPaper()).start()
            }
            R.id.wallpaper_lock -> {
                //锁屏壁纸
                wallpaper_yes_ll.visibility = View.GONE
                Thread(SetScreenWallPaper()).start()
            }
            R.id.wallpaper_all -> {
                //同时设置
                wallpaper_yes_ll.visibility = View.GONE
                Thread(SetAllWallPaper()).start()
            }
        }
    }

    private inner class SetLunchWallPaper : Runnable {
        override fun run() {
            val wallpaper = wallpaperview.getWallPaper()
            val nomalWallPaper = com.jevely.tellu.util.getWallpaper()
            saveWallpaperToLocal(nomalWallPaper)
            com.jevely.tellu.util.setWallpaper(wallpaper)
            ShareTool.getInstance().putBoolean(ShareTool.WALL_PAPER_SET, true)
            ShareTool.getInstance().putString(ShareTool.SET_WALLPAPER_STATE, "1")
            handler?.sendEmptyMessage(0)
        }
    }

    private inner class SetScreenWallPaper : Runnable {
        override fun run() {
            val wallpaper = wallpaperview.getWallPaper()
            val nomalWallPaper = com.jevely.tellu.util.getWallpaper()
            saveWallpaperToLocal(nomalWallPaper)
            com.jevely.tellu.util.setLockWallpaper7(wallpaper)
            ShareTool.getInstance().putBoolean(ShareTool.WALL_PAPER_SET, true)
            ShareTool.getInstance().putString(ShareTool.SET_WALLPAPER_STATE, "2")
            handler?.sendEmptyMessage(0)
        }
    }

    private inner class SetAllWallPaper : Runnable {
        override fun run() {
            val wallpaper = wallpaperview.getWallPaper()
            val nomalWallPaper = com.jevely.tellu.util.getWallpaper()
            saveWallpaperToLocal(nomalWallPaper)
            com.jevely.tellu.util.setAllWallpaper(wallpaper)
            ShareTool.getInstance().putBoolean(ShareTool.WALL_PAPER_SET, true)
            ShareTool.getInstance().putString(ShareTool.SET_WALLPAPER_STATE, "3")
            handler?.sendEmptyMessage(0)
        }
    }

    override fun requestSuccess(requestCode: Int, permission: List<String>) {
        super.requestSuccess(requestCode, permission)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Thread(SetLunchWallPaper()).start()
        } else {
            wallpaper_yes_ll.visibility = View.VISIBLE
        }
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
