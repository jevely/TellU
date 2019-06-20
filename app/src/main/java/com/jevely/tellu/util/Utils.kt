package com.jevely.tellu.util

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Point
import com.jevely.tellu.TellUApplication


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
fun getVersion():String{
    val manager = TellUApplication.getContext().getPackageManager();
    try {
        val info = manager.getPackageInfo(TellUApplication.getContext().getPackageName(), 0);
        val name = info.versionName
        return name
    } catch (e : Exception) {
        e.printStackTrace()
        return "1.0.0"
    }
}