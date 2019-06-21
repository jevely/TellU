package com.jevely.tellu.util

import android.annotation.TargetApi
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Environment
import com.jevely.tellu.TellUApplication
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


//获取屏幕高宽
fun getScreen(): Point {
    val dm = TellUApplication.getContext().getResources().getDisplayMetrics()
    val heigth = dm.heightPixels
    val width = dm.widthPixels
    val point = Point(heigth, width)
    return point
}

//dp2px
fun dp2px(dipValue: Float): Int {
    val scale = TellUApplication.getContext().getResources().getDisplayMetrics().density
    return (dipValue * scale + 0.5f).toInt()
}

//px2dp
fun px2dp(pxValue: Float): Int {
    val scale = TellUApplication.getContext().getResources().getDisplayMetrics().density
    return (pxValue / scale + 0.5f).toInt()
}

//version
fun getVersion(): String {
    val manager = TellUApplication.getContext().getPackageManager();
    try {
        val info = manager.getPackageInfo(TellUApplication.getContext().getPackageName(), 0);
        val name = info.versionName
        return name
    } catch (e: Exception) {
        e.printStackTrace()
        return "1.0.0"
    }
}

val saveAddress =
    Environment.getExternalStorageDirectory().absolutePath + File.separator + "wallpaper" + File.separator + "real.jpg"
val saveFloadAddress =
    Environment.getExternalStorageDirectory().absolutePath + File.separator + "wallpaper" + File.separator

//图片保存到本地
fun saveWallpaperToLocal(bitmap: Bitmap) {
    val wallpaperFile = creatLocalWallpaper()
    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(wallpaperFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            if (fos != null) {
                fos.flush()
                fos.fd.sync()
                fos.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

//本地读取图片
fun getWallpaperFromLocal(): Bitmap {
    val bitmap = BitmapFactory.decodeFile(saveAddress)
    return bitmap
}


/**
 * 创建本地图片
 */
fun creatLocalWallpaper(): File {
    val mediaFile = File(saveFloadAddress)
    if (!mediaFile.exists()) {
        mediaFile.mkdirs()
    }
    val imgFile = File(saveAddress)
    imgFile.delete()

    return imgFile
}

/**
 * 删除文件
 */
fun deleteFile(file: File): Boolean {
    if (file.isFile) {
        file.delete()
    } else if (file.isDirectory) {
        val files = file.listFiles()
        for (file1 in files) {
            deleteFile(file1)
        }
    }
    return file.exists()
}

//获取本地壁纸
fun getWallpaper(): Bitmap {
    val wallpaperManager = WallpaperManager.getInstance(TellUApplication.getContext())
    val wallpaperDrawable = wallpaperManager.drawable
    // 将Drawable,转成Bitmap
    val bm = (wallpaperDrawable as BitmapDrawable).bitmap
    return bm
}

//设置桌面壁纸
fun setWallpaper(bitmap: Bitmap) {
    try {
        val wallpaperManager = WallpaperManager.getInstance(TellUApplication.getContext())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val inputStream = ByteArrayInputStream(baos.toByteArray())

            wallpaperManager.setStream(inputStream, null, true, WallpaperManager.FLAG_SYSTEM)
        } else {
            wallpaperManager.setBitmap(bitmap)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

//设置锁屏壁纸
@TargetApi(Build.VERSION_CODES.N)
fun setLockWallpaper7(bitmap: Bitmap) {
    try {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val inputStream = ByteArrayInputStream(baos.toByteArray())

        val wallpaperManager = WallpaperManager.getInstance(TellUApplication.getContext())

        wallpaperManager.setStream(inputStream, null, true, WallpaperManager.FLAG_LOCK)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
