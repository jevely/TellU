package com.jevely.tellu.view

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.jevely.tellu.R

class WallPaperView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val wallpaper_back: RelativeLayout
    private val wallpaper_content: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.wallpaper_layout, null)
        wallpaper_back = view.findViewById(R.id.wallpaper_back)
        wallpaper_content = view.findViewById(R.id.wallpaper_content)
        addView(view)
    }

    fun setContent(content: String) {
        wallpaper_content.text = content
    }

    fun getWallPaper(): Bitmap {
        setDrawingCacheEnabled(true)
        var bitmap = Bitmap.createBitmap(getDrawingCache())
        setDrawingCacheEnabled(false)
        bitmap = Bitmap.createBitmap(bitmap)
        return bitmap
    }

}